package com.aviator.mywebsite.db.datasource.unpooled;

import com.aviator.mywebsite.db.datasource.DataSourceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UnPooledDataSource
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/17 8:51
 */
public class UnPooledDataSource implements DataSource {

    private static final Logger log = LoggerFactory.getLogger(UnPooledDataSource.class);

    private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap<>();

    private String driver;
    private String url;
    private String username;
    private String password;

    private Boolean autoCommit;
    private Integer defaultTransactionIsolationLevel;

    static {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            registeredDrivers.put(driver.getClass().getName(), driver);
        }
    }

    public UnPooledDataSource() {
    }

    public UnPooledDataSource(String driver, String url, String username, String password, Boolean autoCommit, Integer defaultTransactionIsolationLevel) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.autoCommit = autoCommit;
        this.defaultTransactionIsolationLevel = defaultTransactionIsolationLevel;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return doGetConnection(username, password);
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

    private void initializeDriver() throws DataSourceException {
        try {
            if (StringUtils.isNotBlank(driver)) {
                if (!registeredDrivers.containsKey(driver)) {
                    Class<?> driverType = Class.forName(driver);
                    Driver driverInstance = (Driver) driverType.newInstance();
                    DriverManager.registerDriver(driverInstance);
                    registeredDrivers.put(driver, driverInstance);
                }
            }
        } catch (Exception e) {
            log.error("init driver error on UnPooledDataSource", e);
            throw new DataSourceException("init driver error on UnPooledDataSource: ", e);
        }
    }

    private Connection doGetConnection(String username, String password) throws SQLException {
        initializeDriver();
        Connection connection = DriverManager.getConnection(url, username, password);
        configureConnection(connection);
        return connection;
    }

    private void configureConnection(Connection connection) throws SQLException {
        if (autoCommit != null && autoCommit != connection.getAutoCommit()) {
            connection.setAutoCommit(autoCommit);
        }
        if (defaultTransactionIsolationLevel != null) {
            connection.setTransactionIsolation(defaultTransactionIsolationLevel);
        }
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
}
