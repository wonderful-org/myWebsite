package com.aviator.mywebsite.db.datasource.pooled;

import com.aviator.mywebsite.db.datasource.unpooled.UnPooledDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName PooledDataSource
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/18 10:46
 */
public class PooledDataSource implements DataSource {

    private static Logger log = LoggerFactory.getLogger(PooledDataSource.class);

    private PoolState state = new PoolState(this);

    private UnPooledDataSource dataSource;

    // 连接池最大连接数
    private int poolMaximumActiveConnections = 10;

    // 连接池最大空闲连接数
    private int poolMaximumIdleConnections = 3;

    // 最长被检出时间，如果线程饱和需要新建连接，则检查最早创建的活动连接，如果超过检出时间，则从连接池中删除并回滚(如需要)，然后新建连接
    private int poolMaximumCheckoutTime = 20000;

    // 无法获取且无法创建新连接时的等待时间
    protected int poolTimeToWait = 20000;

    // 可以容忍的错误连接数
    private int poolMaximumLocalBadConnectionTolerance = 3;

    // 是否开启连接检查
    private boolean poolPingEnable;

    // ping查询语句
    private String poolPingQuery = "NO PING QUERY SET";

    // ping侦察频率
    private int poolPingConnectionsNotUsedFor;

    // connection类型编码
    private Integer expectedConnectionTypeCode;

    public PooledDataSource() {
        this.dataSource = new UnPooledDataSource();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(dataSource.getUsername(), dataSource.getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return popConnection(username, password).getProxyConnection();
    }

    private PooledConnection popConnection(String username, String password) throws SQLException {
        PooledConnection conn = null;
        long startTime = System.currentTimeMillis();
        int localBadConnectionCount = 0;
        while (conn == null) {
            synchronized (state) {
                // 如果连接池有空闲连接，则取出
                if (!state.idleConnections.isEmpty()) {
                    conn = state.idleConnections.remove(0);
                } else {
                    // 没有空闲连接，则判断活跃的连接数是否已达上限，如果没有则新建连接
                    if (state.activeConnections.size() < poolMaximumActiveConnections) {
                        conn = new PooledConnection(this, dataSource.getConnection());
                    } else {
                        // 活跃连接数已达最大值，检查最早活跃连接是否超时
                        PooledConnection oldConn = state.activeConnections.get(0);
                        long oldConnCheckoutTimestamp = System.currentTimeMillis() - oldConn.getCheckoutTimestamp();
                        // 如果超时，从活跃连接池中删除该连接，需要回滚就回滚，然后创建新连接
                        if (oldConnCheckoutTimestamp > poolMaximumCheckoutTime) {
                            state.claimedOverdueConnectionCount++;
                            state.activeConnections.remove(oldConn);
                            if (!oldConn.getRealConnection().getAutoCommit()) {
                                oldConn.getRealConnection().rollback();
                            }
                            // 创建新连接，并将旧的超时连接设为无效的
                            conn = new PooledConnection(this, dataSource.getConnection());
                            conn.setCreatedTimestamp(oldConn.getCreatedTimestamp());
                            conn.setLastUsedTimestamp(oldConn.getLastUsedTimestamp());
                            oldConn.invalidate();
                        } else {
                            // 没有空闲连接，活跃连接数达到最大并且没有超时连接，则线程等待
                            try {
                                state.wait(poolTimeToWait);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                }
                if (conn != null) {
                    if (conn.isValid()) {
                        if (!conn.getRealConnection().getAutoCommit()) {
                            conn.getRealConnection().rollback();
                        }
                        conn.setConnectionTypeCode(assembleConnectionTypeCode(dataSource.getUrl(), username, password));
                        conn.setCheckoutTimestamp(System.currentTimeMillis());
                        conn.setLastUsedTimestamp(System.currentTimeMillis());
                        state.activeConnections.add(conn);
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("A bad connection (" + conn.getRealHashCode() + ") was returned from the pool, getting another connection.");
                        }
                        state.badConnectionCount++;
                        localBadConnectionCount++;
                        conn = null;
                        if (localBadConnectionCount > poolMaximumLocalBadConnectionTolerance + poolMaximumIdleConnections) {
                            log.error("PooledDataSource: Could not get a good connection to the database.");
                            throw new SQLException("PooledDataSource: Could not get a good connection to the database.");
                        }
                    }
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("PooledDataSource get a connection in " + (System.currentTimeMillis() - startTime) + " millis");
        }
        if (conn == null) {
            log.error("PooledDataSource: Unknown severe error condition.  The connection pool returned a null connection.");
            throw new SQLException("PooledDataSource: Unknown severe error condition.  The connection pool returned a null connection.");
        }
        return conn;
    }

    protected void pushConnection(PooledConnection conn) throws SQLException {
        // 从活跃连接队列中去除该连接
        state.activeConnections.remove(conn);
        // 检查连接是否有效，无效则在state中新增一条坏连接记录，有效判断连接池空闲连接是否达到上限，没有则新增连接放入，并唤醒等待获取连接的其他线程
        synchronized (state) {
            if (conn.isValid()) {
                if (state.idleConnections.size() < poolMaximumIdleConnections && conn.getConnectionTypeCode() == getExpectedConnectionTypeCode()) {
                    Connection realConn = conn.getRealConnection();
                    if (!realConn.getAutoCommit()) {
                        realConn.rollback();
                    }
                    PooledConnection newConn = new PooledConnection(this, realConn);
                    newConn.setCreatedTimestamp(conn.getCreatedTimestamp());
                    newConn.setLastUsedTimestamp(conn.getLastUsedTimestamp());
                    state.idleConnections.add(newConn);
                    conn.invalidate();
                    if (log.isDebugEnabled()) {
                        log.debug("Return connection " + newConn.getRealHashCode() + " to pool.");
                    }
                    state.notifyAll();
                } else {
                    Connection realConn = conn.getRealConnection();
                    if (!realConn.getAutoCommit()) {
                        realConn.rollback();
                    }
                    conn.invalidate();
                    if (log.isDebugEnabled()) {
                        log.debug("Closed connection " + conn.getRealHashCode() + ".");
                    }
                }
            } else {
                state.badConnectionCount++;
                if (log.isDebugEnabled()) {
                    log.debug("A bad connection (" + conn.getRealHashCode() + ") attempted to return to the pool, discarding connection.");
                }
            }
        }
    }

    public void forceCloseAll() {
        synchronized (state) {
            expectedConnectionTypeCode = getExpectedConnectionTypeCode();
            for (int i = state.activeConnections.size(); i > 0; i--) {
                try {
                    PooledConnection conn = state.activeConnections.remove(i - 1);
                    conn.invalidate();

                    Connection realConn = conn.getRealConnection();
                    if (!realConn.getAutoCommit()) {
                        realConn.rollback();
                    }
                    realConn.close();
                } catch (Exception e) {
                    // ignore
                }
            }
            for (int i = state.idleConnections.size(); i > 0; i--) {
                try {
                    PooledConnection conn = state.idleConnections.remove(i - 1);
                    conn.invalidate();

                    Connection realConn = conn.getRealConnection();
                    if (!realConn.getAutoCommit()) {
                        realConn.rollback();
                    }
                    realConn.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("PooledDataSource forcefully closed/removed all connections.");
        }
    }

    protected boolean pingConnection(PooledConnection conn) {
        boolean result = false;
        try {
            result = !conn.getRealConnection().isClosed();
        } catch (SQLException e) {
            if (log.isDebugEnabled()) {
                log.debug("Connection " + conn.getRealHashCode() + " is BAD: " + e.getMessage());
            }
        }
        if (result) {
            if (poolPingEnable && poolPingConnectionsNotUsedFor >= 0 && conn.getTimeElapsedSinceLastUse() > poolPingConnectionsNotUsedFor) {
                Connection realConnection = null;
                try {
                    realConnection = conn.getRealConnection();
                    try (Statement statement = realConnection.createStatement()) {
                        statement.executeQuery(poolPingQuery).close();
                    }
                    if (!realConnection.getAutoCommit()) {
                        realConnection.rollback();
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("Connection " + conn.getRealHashCode() + " is Good!");
                    }
                    result = true;
                } catch (Exception e) {
                    log.warn("Execution of ping query '" + poolPingQuery + "' failed: " + e.getMessage());
                    if (log.isDebugEnabled()) {
                        log.debug("Connection " + conn.getRealHashCode() + " is Bad:" + e.getMessage());
                    }
                    if (realConnection != null) {
                        try {
                            realConnection.close();
                        } catch (SQLException e1) {
                        }
                    }
                    result = false;
                }
            }
        }
        return result;
    }

    private int assembleConnectionTypeCode(String url, String username, String password) {
        return ("" + url + username + password).hashCode();
    }

    public Integer getExpectedConnectionTypeCode() {
        if (expectedConnectionTypeCode == null) {
            if (StringUtils.isNotBlank(dataSource.getUrl()) && StringUtils.isNotBlank(dataSource.getUsername()) && StringUtils.isNotBlank(dataSource.getPassword())) {
                expectedConnectionTypeCode = assembleConnectionTypeCode(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
            }
        }
        return expectedConnectionTypeCode;
    }

    public PoolState getState() {
        return state;
    }

    public void setPoolMaximumActiveConnections(int poolMaximumActiveConnections) {
        this.poolMaximumActiveConnections = poolMaximumActiveConnections;
        forceCloseAll();
    }

    public void setPoolMaximumIdleConnections(int poolMaximumIdleConnections) {
        this.poolMaximumIdleConnections = poolMaximumIdleConnections;
        forceCloseAll();
    }

    public void setPoolMaximumCheckoutTime(int poolMaximumCheckoutTime) {
        this.poolMaximumCheckoutTime = poolMaximumCheckoutTime;
        forceCloseAll();
    }

    public void setPoolTimeToWait(int poolTimeToWait) {
        this.poolTimeToWait = poolTimeToWait;
        forceCloseAll();
    }

    public void setPoolMaximumLocalBadConnectionTolerance(int poolMaximumLocalBadConnectionTolerance) {
        this.poolMaximumLocalBadConnectionTolerance = poolMaximumLocalBadConnectionTolerance;
    }

    public void setPoolPingEnable(boolean poolPingEnable) {
        this.poolPingEnable = poolPingEnable;
        forceCloseAll();
    }

    public void setPoolPingQuery(String poolPingQuery) {
        this.poolPingQuery = poolPingQuery;
        forceCloseAll();
    }

    public void setPoolPingConnectionsNotUsedFor(int poolPingConnectionsNotUsedFor) {
        this.poolPingConnectionsNotUsedFor = poolPingConnectionsNotUsedFor;
        forceCloseAll();
    }

    public void setDriver(String driver) {
        dataSource.setDriver(driver);
        forceCloseAll();
    }

    public void setUrl(String url) {
        dataSource.setUrl(url);
        forceCloseAll();
    }

    public void setUsername(String username) {
        dataSource.setUsername(username);
        forceCloseAll();
    }

    public void setPassword(String password) {
        dataSource.setPassword(password);
        forceCloseAll();
    }

    public void setAutoCommit(Boolean autoCommit) {
        dataSource.setAutoCommit(autoCommit);
        forceCloseAll();
    }

    public void setDefaultTransactionIsolationLevel(Integer defaultTransactionIsolationLevel) {
        dataSource.setDefaultTransactionIsolationLevel(defaultTransactionIsolationLevel);
        forceCloseAll();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException(getClass().getName() + " is not a wrapper.");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public java.util.logging.Logger getParentLogger() {
        return java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
    }

    @Override
    protected void finalize() throws Throwable {
        forceCloseAll();
        super.finalize();
    }
}
