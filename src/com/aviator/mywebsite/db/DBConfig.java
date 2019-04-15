package com.aviator.mywebsite.db;

/**
 * @ClassName DBConfig
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/15 19:44
 */
public class DBConfig {

    private String url;

    private String userName;

    private String password;

    private String driverClass;

    public DBConfig() {
    }

    public DBConfig(String url, String userName, String password, String driverClass) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
