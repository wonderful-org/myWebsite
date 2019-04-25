package com.aviator.mywebsite.constant;

/**
 * @ClassName DBConstants
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/17 10:58
 */
public class DBConstants {

    public interface DATA_SOURCE {

        String PROPERTIES_NAME = "jdbc.properties";

        interface PROPERTIES {

            String DRIVER = "driver";

            String USERNAME = "username";

            String PASSWORD = "password";

            String URL = "url";

            String AUTO_COMMIT = "autocommit";

            String TRANSACTION_ISOLATION_LEVEL = "transaction";

            /**
             * 数据源类型，pooled或unPooled
             */
            String DATA_SOURCE_TYPE = "datasourcetype";

            /**
             * 批处理单次处理数量
             */
            String EXECUTE_BATCH_SIZE = "batchsize";

            /**
             * 连接池最大活跃连接数
             */
            String POOL_MAXIMUM_ACTIVE_CONNECTIONS = "poolmaximumactiveconnections";

            /**
             * 连接池最大空闲连接数
             */
            String POOL_MAXIMUM_IDLE_CONNECTIONS = "poolmaximumidleconnections";

            /**
             * 最长被检出时间
             */
            String POOL_MAXIMUM_CHECKOUT_TIME = "poolmaximumcheckouttime";

            /**
             * 无法获取且无法创建新连接时的等待时间
             */
            String POOL_TIME_TO_WAIT = "pooltimetowait";

            /**
             * 可以容忍的错误连接数
             */
            String POOL_MAXIMUM_LOCAL_BAD_CONNECTION_TOLERANCE = "poolmaximumlocalbadconnectiontolerance";

            /**
             * 是否开启连接检查
             */
            String POOL_PING_ENABLE = "poolpingenable";

            /**
             * ping查询语句
             */
            String POOL_PING_QUERY = "poolpingquery";

            /**
             * ping侦察频率
             */
            String POOL_PING_CONNECTIONS_NOT_USED_FOR = "poolpingconnectionsnotusedfor";

        }
    }
}
