package com.aviator.mywebsite.db;

import com.aviator.mywebsite.constant.DBConstants;
import com.aviator.mywebsite.db.datasource.DataSourceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @ClassName ConfigBuilder
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/19 20:31
 */
public class ConfigBuilder {

    private static final Logger log = LoggerFactory.getLogger(ConfigBuilder.class);

    public static DBConfig build(Properties properties) {
        DBConfig configTemp = new DBConfig();
        for (Object key : properties.keySet()) {
            String propertyName = key.toString();
            String propertyLowerName = propertyName.toLowerCase();
            if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.DRIVER)) {
                configTemp.setDriver(properties.getProperty(propertyName));
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.URL)) {
                configTemp.setUrl(properties.getProperty(propertyName));
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.USERNAME)) {
                configTemp.setUsername(properties.getProperty(propertyName));
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.PASSWORD)) {
                configTemp.setPassword(properties.getProperty(propertyName));
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.AUTO_COMMIT)) {
                String autoCommitStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(autoCommitStr)) {
                    configTemp.setAutoCommit(Boolean.parseBoolean(autoCommitStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.TRANSACTION_ISOLATION_LEVEL)) {
                String transactionIsolationLevelStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(transactionIsolationLevelStr)) {
                    configTemp.setDefaultTransactionIsolationLevel(Integer.parseInt(transactionIsolationLevelStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.EXECUTE_BATCH_SIZE)) {
                String batchSizeStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(batchSizeStr)) {
                    configTemp.setBatchSize(Integer.parseInt(batchSizeStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_MAXIMUM_ACTIVE_CONNECTIONS)) {
                String poolMaximumActiveConnectionsStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(poolMaximumActiveConnectionsStr)) {
                    configTemp.setPoolMaximumActiveConnections(Integer.parseInt(poolMaximumActiveConnectionsStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_MAXIMUM_IDLE_CONNECTIONS)) {
                String poolMaximumIdleConnectionsStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(poolMaximumIdleConnectionsStr)) {
                    configTemp.setPoolMaximumIdleConnections(Integer.parseInt(poolMaximumIdleConnectionsStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_MAXIMUM_CHECKOUT_TIME)) {
                String poolMaximumCheckoutTimeStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(poolMaximumCheckoutTimeStr)) {
                    configTemp.setPoolMaximumCheckoutTime(Integer.parseInt(poolMaximumCheckoutTimeStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_TIME_TO_WAIT)) {
                String poolTimeToWaitStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(poolTimeToWaitStr)) {
                    configTemp.setPoolTimeToWait(Integer.parseInt(poolTimeToWaitStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_MAXIMUM_LOCAL_BAD_CONNECTION_TOLERANCE)) {
                String poolMaximumLocalBadConnectionToleranceStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(poolMaximumLocalBadConnectionToleranceStr)) {
                    configTemp.setPoolMaximumLocalBadConnectionTolerance(Integer.parseInt(poolMaximumLocalBadConnectionToleranceStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_PING_ENABLE)) {
                String poolPingEnableStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(poolPingEnableStr)) {
                    configTemp.setPoolPingEnable(Boolean.parseBoolean(poolPingEnableStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_PING_QUERY)) {
                String poolPingQuery = properties.getProperty(propertyName);
                configTemp.setPoolPingQuery(poolPingQuery);
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.POOL_PING_CONNECTIONS_NOT_USED_FOR)) {
                String poolPingConnectionsNotUsedForStr = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(poolPingConnectionsNotUsedForStr)) {
                    configTemp.setPoolPingConnectionsNotUsedFor(Integer.parseInt(poolPingConnectionsNotUsedForStr));
                }
            } else if (propertyLowerName.contains(DBConstants.DATA_SOURCE.PROPERTIES.DATA_SOURCE_TYPE)) {
                String dataSourceType = properties.getProperty(propertyName);
                if (StringUtils.isNotBlank(dataSourceType) && dataSourceType.equals("pooled")) {
                    configTemp.setDataSourceType(true);
                }
            } else {
                log.error("Unknown dataSource property: " + propertyName);
                throw new DataSourceException("Unknown dataSource property: " + propertyName);
            }
        }
        return configTemp;
    }
}
