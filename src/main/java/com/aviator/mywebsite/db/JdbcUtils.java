package com.aviator.mywebsite.db;

import com.aviator.mywebsite.constant.DBConstants;
import com.aviator.mywebsite.db.datasource.DataSourceFactory;
import com.aviator.mywebsite.db.datasource.pooled.PooledDataSourceFactory;
import com.aviator.mywebsite.db.datasource.unpooled.UnPooledDataSourceFactory;
import com.aviator.mywebsite.db.executor.Executor;
import com.aviator.mywebsite.db.executor.SimpleExecutor;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName JdbcUtils
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/19 11:37
 */
public class JdbcUtils {

    private static final Logger log = LoggerFactory.getLogger(JdbcUtils.class);

    private static Properties properties;

    private static DBConfig dbConfig;

    private static DataSource dataSource;

    private static Executor executor;

    static {
        findProperties();
        DataSourceFactory dataSourceFactory = getDataSourceFactory();
        dataSourceFactory.setProperties(properties);
        dataSource = dataSourceFactory.getDataSource();
        executor = new SimpleExecutor(dataSource, dbConfig);
    }

    /**
     * 重载executeInsert(String sql, List params)方法
     *
     * @param sql
     * @return
     */
    public static int executeInsert(String sql, Object... params) {
        List list = ArrayUtils.isEmpty(params) ? null : Arrays.asList(params);
        return executeInsert(sql, list);
    }

    /**
     * 新增数据，返回新增数量
     *
     * @param sql
     * @param params
     * @return
     */
    public static int executeInsert(String sql, List params) {
        try {
            return executor.executeInsert(sql, params);
        } catch (Exception e) {
            throw new DataBaseException("executeInsert error", e);
        }
    }

    /**
     * 重载executeInsert(String sql, List params, Class<T> idType)方法
     *
     * @param sql
     * @return
     */
    public static <T> T executeInsert(String sql, Class<T> idType, Object... params) {
        List list = ArrayUtils.isEmpty(params) ? null : Arrays.asList(params);
        return executeInsert(sql, list, idType);
    }

    /**
     * 新增数据，返回主键
     *
     * @param sql
     * @param params
     * @param idType
     * @param <T>
     * @return
     */
    public static <T> T executeInsert(String sql, List params, Class<T> idType) {
        try {
            return executor.executeInsert(sql, params, idType);
        } catch (Exception e) {
            throw new DataBaseException("executeInsert error", e);
        }
    }

    /**
     * 重载executeUpdate(String sql, List params)方法
     *
     * @param sql
     * @return
     */
    public static int executeUpdate(String sql, Object... params) {
        List list = ArrayUtils.isEmpty(params) ? null : Arrays.asList(params);
        try {
            return executor.executeUpdate(sql, list);
        } catch (Exception e) {
            throw new DataBaseException("executeUpdate error", e);
        }
    }

    /**
     * 修改数据，返回修改数量
     *
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, List params) {
        try {
            return executor.executeUpdate(sql, params);
        } catch (Exception e) {
            throw new DataBaseException("executeUpdate error", e);
        }
    }

    /**
     * 批处理
     *
     * @param sql
     * @param paramss
     */
    public static void batchUpdate(String sql, List<List> paramss) {
        try {
            executor.batchUpdate(sql, paramss);
        } catch (Exception e) {
            throw new DataBaseException("batchUpdate error", e);
        }
    }

    /**
     * 重载executeQuery(String sql, List params)
     *
     * @param sql
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List list = ArrayUtils.isEmpty(params) ? null : Arrays.asList(params);
        return executeQuery(sql, list);
    }

    /**
     * 数据查询
     *
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, List params) {
        try {
            return executor.executeQuery(sql, params);
        } catch (Exception e) {
            throw new DataBaseException("executeQuery error", e);
        }
    }

    /**
     * 重载executeQueryForObject(String sql, List params, Class<T> resultType)
     *
     * @param sql
     * @param resultType
     * @param <T>
     * @return
     */
    public static <T> T executeQueryForObject(String sql, Class<T> resultType, Object... params) {
        List list = ArrayUtils.isEmpty(params) ? null : Arrays.asList(params);
        return executeQueryForObject(sql, list, resultType);
    }

    /**
     * 查询单条数据，返回resultType类型
     *
     * @param sql
     * @param params
     * @param resultType
     * @param <T>
     * @return
     */
    public static <T> T executeQueryForObject(String sql, List params, Class<T> resultType) {
        try {
            return executor.executeQueryForObject(sql, params, resultType);
        } catch (Exception e) {
            throw new DataBaseException("executeQueryForObject error", e);
        }
    }

    /**
     * 重载executeQueryForList(String sql, List params, Class<T> resultType)
     *
     * @param sql
     * @param resultType
     * @param <T>
     * @return
     */
    public static <T> List<T> executeQueryForList(String sql, Class<T> resultType, Object... params) {
        List list = ArrayUtils.isEmpty(params) ? null : Arrays.asList(params);
        return executeQueryForList(sql, list, resultType);
    }

    /**
     * 返回多条数据，返回resultType类型
     *
     * @param sql
     * @param params
     * @param resultType
     * @param <T>
     * @return
     */
    public static <T> List<T> executeQueryForList(String sql, List params, Class<T> resultType) {
        try {
            return executor.executeQueryForList(sql, params, resultType);
        } catch (Exception e) {
            throw new DataBaseException("executeQueryForList error", e);
        }
    }

    private static void findProperties() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(DBConstants.DATA_SOURCE.PROPERTIES_NAME);
        if (in == null) {
            log.error("can not find " + DBConstants.DATA_SOURCE.PROPERTIES_NAME);
            throw new DataBaseException("can not find " + DBConstants.DATA_SOURCE.PROPERTIES_NAME);
        }
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            log.error("load properties error: ", e);
            throw new DataBaseException("load properties error: ", e);
        }
        checkProperties(prop);
        properties = prop;
        parseProperties(properties);
    }

    private static void checkProperties(Properties prop) {
        if (prop == null || prop.isEmpty()) {
            log.error("load properties is empty! ");
            throw new DataBaseException("load properties is empty");
        }
    }

    private static void parseProperties(Properties properties) {
        dbConfig = ConfigBuilder.build(properties);
    }

    private static DataSourceFactory getDataSourceFactory() {
        if (dbConfig.isDataSourceType()) {
            return new PooledDataSourceFactory();
        }
        return new UnPooledDataSourceFactory();
    }
}
