package com.aviator.mywebsite.db.datasource;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceFactory {

    void setProperties(Properties properties);

    DataSource getDataSource();
}
