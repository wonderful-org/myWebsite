package com.aviator.mywebsite.db.datasource.pooled;

import com.aviator.mywebsite.db.datasource.DataSourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName PooledConnection
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/17 22:17
 */
public class PooledConnection implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(PooledConnection.class);

    private PooledDataSource dataSource;

    private Connection realConnection;

    private Connection proxyConnection;

    private long checkoutTimestamp;

    private long createdTimestamp;

    private long lastUsedTimestamp;

    private int connectionTypeCode;

    private boolean valid;

    private final int hashcode;

    public PooledConnection(PooledDataSource dataSource, Connection realConnection) {
        this.hashcode = realConnection.hashCode();
        this.dataSource = dataSource;
        this.realConnection = realConnection;
        this.valid = true;
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class<?>[]{Connection.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws SQLException {
        String methodName = method.getName();
        if ("close".hashCode() == methodName.hashCode() && "close".equals(methodName)) {
            dataSource.pushConnection(this);
            return null;
        }
        if (!Object.class.equals(method.getDeclaringClass())) {
            // issue #579 toString() should never fail
            // throw an SQLException instead of a Runtime
            checkConnection();
        }
        try {
            return method.invoke(realConnection, args);
        } catch (Exception e) {
            log.error("PoolConnection method invoke error", e);
            throw new DataSourceException("PoolConnection method invoke error: ", e);
        }
    }

    public long getCheckoutTimestamp() {
        return checkoutTimestamp;
    }

    public void setCheckoutTimestamp(long checkoutTimestamp) {
        this.checkoutTimestamp = checkoutTimestamp;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getLastUsedTimestamp() {
        return lastUsedTimestamp;
    }

    public void setLastUsedTimestamp(long lastUsedTimestamp) {
        this.lastUsedTimestamp = lastUsedTimestamp;
    }

    public int getConnectionTypeCode() {
        return connectionTypeCode;
    }

    public void setConnectionTypeCode(int connectionTypeCode) {
        this.connectionTypeCode = connectionTypeCode;
    }

    public PooledDataSource getDataSource() {
        return dataSource;
    }

    public Connection getRealConnection() {
        return realConnection;
    }

    public Connection getProxyConnection() {
        return proxyConnection;
    }

    public boolean isValid() {
        return valid && realConnection != null && dataSource.pingConnection(this);
    }

    public int getRealHashCode() {
        return realConnection == null ? 0 : realConnection.hashCode();
    }

    public long getTimeElapsedSinceLastUse() {
        return System.currentTimeMillis() - lastUsedTimestamp;
    }

    public void invalidate() {
        this.valid = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PooledConnection) {
            return hashcode == ((PooledConnection) o).getRealHashCode();
        } else if (o instanceof Connection) {
            return hashcode == o.hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    private void checkConnection() throws SQLException {
        if (!valid) {
            log.error("Error accessing PooledConnection. Connection is invalid");
            throw new SQLException("Error accessing PooledConnection. Connection is invalid.");
        }
    }
}
