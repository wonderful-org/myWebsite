package com.aviator.mywebsite.db;

/**
 * @ClassName DBConfig
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/19 12:38
 */
public class DBConfig {

    private Integer batchSize = 200;

    private String driver;

    private String url;

    private String username;

    private String password;

    private Boolean autoCommit;

    private Integer defaultTransactionIsolationLevel;

    // 数据源类型，false:unPooled, true:pooled
    private boolean dataSourceType;

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

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public Integer getDefaultTransactionIsolationLevel() {
        return defaultTransactionIsolationLevel;
    }

    public void setDefaultTransactionIsolationLevel(Integer defaultTransactionIsolationLevel) {
        this.defaultTransactionIsolationLevel = defaultTransactionIsolationLevel;
    }

    public int getPoolMaximumActiveConnections() {
        return poolMaximumActiveConnections;
    }

    public void setPoolMaximumActiveConnections(int poolMaximumActiveConnections) {
        this.poolMaximumActiveConnections = poolMaximumActiveConnections;
    }

    public int getPoolMaximumIdleConnections() {
        return poolMaximumIdleConnections;
    }

    public void setPoolMaximumIdleConnections(int poolMaximumIdleConnections) {
        this.poolMaximumIdleConnections = poolMaximumIdleConnections;
    }

    public int getPoolMaximumCheckoutTime() {
        return poolMaximumCheckoutTime;
    }

    public void setPoolMaximumCheckoutTime(int poolMaximumCheckoutTime) {
        this.poolMaximumCheckoutTime = poolMaximumCheckoutTime;
    }

    public int getPoolTimeToWait() {
        return poolTimeToWait;
    }

    public void setPoolTimeToWait(int poolTimeToWait) {
        this.poolTimeToWait = poolTimeToWait;
    }

    public int getPoolMaximumLocalBadConnectionTolerance() {
        return poolMaximumLocalBadConnectionTolerance;
    }

    public void setPoolMaximumLocalBadConnectionTolerance(int poolMaximumLocalBadConnectionTolerance) {
        this.poolMaximumLocalBadConnectionTolerance = poolMaximumLocalBadConnectionTolerance;
    }

    public boolean isPoolPingEnable() {
        return poolPingEnable;
    }

    public void setPoolPingEnable(boolean poolPingEnable) {
        this.poolPingEnable = poolPingEnable;
    }

    public String getPoolPingQuery() {
        return poolPingQuery;
    }

    public void setPoolPingQuery(String poolPingQuery) {
        this.poolPingQuery = poolPingQuery;
    }

    public int getPoolPingConnectionsNotUsedFor() {
        return poolPingConnectionsNotUsedFor;
    }

    public void setPoolPingConnectionsNotUsedFor(int poolPingConnectionsNotUsedFor) {
        this.poolPingConnectionsNotUsedFor = poolPingConnectionsNotUsedFor;
    }

    public boolean isDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(boolean dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
}
