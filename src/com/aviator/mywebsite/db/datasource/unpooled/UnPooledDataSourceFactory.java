package com.aviator.mywebsite.db.datasource.unpooled;

import com.aviator.mywebsite.db.ConfigBuilder;
import com.aviator.mywebsite.db.DBConfig;
import com.aviator.mywebsite.db.datasource.DataSourceException;
import com.aviator.mywebsite.db.datasource.DataSourceFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @ClassName UnPooledDataSourceFactory
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/17 10:52
 */
public class UnPooledDataSourceFactory implements DataSourceFactory {

    private static final Logger log = LoggerFactory.getLogger(UnPooledDataSourceFactory.class);

    protected DataSource dataSource;

    private Properties properties;

    public UnPooledDataSourceFactory() {
        this.dataSource = new UnPooledDataSource();
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
        convertProperties();
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    private void convertProperties() {
        convertProperties(properties);
    }

    private void convertProperties(Properties properties) {
        DBConfig config = ConfigBuilder.build(properties);
        try {
            configureDataSource(config);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("configureDataSource error", e);
            throw new DataSourceException("configureDataSource error: ", e);
        }
    }

    private void configureDataSource(DBConfig config) throws InvocationTargetException, IllegalAccessException {
//        checkDataSourceParams();
        Method[] methods = config.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            String fieldName;
            if (methodName.startsWith("is")) {
                fieldName = toLowerCaseFirstOne(methodName.substring(2));
            } else if (methodName.startsWith("get")) {
                fieldName = toLowerCaseFirstOne(methodName.substring(3));
            } else {
                continue;
            }
            Object fieldValue = method.invoke(config);
            if (fieldValue != null) {
                try {
                    PropertyUtils.setProperty(dataSource, fieldName, fieldValue);
                } catch (NoSuchMethodException e) {
                    log.debug("configureDataSource NoSuchMethod property: {}", fieldName);
                }
            }
        }
    }

//    private void checkDataSourceParams() {
//        if (StringUtils.isBlank(driver) || StringUtils.isBlank(url) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
//            throw new DataSourceException("jdbc properties \"" + DBConstants.DATA_SOURCE.PROPERTIES_NAME + "\" error");
//        }
//    }

    private String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
