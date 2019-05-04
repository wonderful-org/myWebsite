package com.aviator.mywebsite.db.executor;

import com.aviator.mywebsite.db.DBConfig;
import com.aviator.mywebsite.util.CustomUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SimpleExecutor
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/15 20:34
 */
public class SimpleExecutor extends AbstractExecutor {

    private static final Logger log = LoggerFactory.getLogger(SimpleExecutor.class);

    public SimpleExecutor(DataSource dataSource, DBConfig dbConfig) {
        super(dataSource, dbConfig);
    }

    @Override
    public int executeInsert(String sql, List params) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(sql)) {
            preparedStatementResolver(psmt, params);
            return psmt.executeUpdate();
        } catch (Exception e) {
            log.error("jdbc executeInsert error, sql:{}, params:{}", sql, params, e);
            throw new SQLException("jdbc executeInsert error", e);
        }
    }

    @Override
    public <T> T executeInsert(String sql, List params, Class<T> idType) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatementResolver(psmt, params);
            psmt.executeUpdate();
            rs = psmt.getGeneratedKeys();
            return rs.next() ? (T) typeHandlerRegistry.getTypeHandler(JDBCType.valueOf(rs.getMetaData().getColumnType(1))).getResult(rs, 1) : null;
        } catch (Exception e) {
            log.error("jdbc executeInsert error, sql:{}, params:{}", sql, params, e);
            throw new SQLException("jdbc executeInsert error", e);
        } finally {
            closeResult(rs);
        }
    }

    @Override
    public int executeUpdate(String sql, List params) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(sql)) {
            preparedStatementResolver(psmt, params);
            return psmt.executeUpdate();
        } catch (Exception e) {
            log.error("jdbc executeUpdate error, sql:{}, params:{}", sql, params, e);
            throw new SQLException("jdbc executeUpdate error", e);
        }
    }

    @Override
    public void batchUpdate(String sql, List<List> paramss) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (int i = 0; i < paramss.size(); i++) {
                List params = paramss.get(i);
                preparedStatementResolver(psmt, params);
                psmt.addBatch();
                if (i > dbConfig.getBatchSize() || i == (paramss.size() - 1)) {
                    psmt.executeBatch();
                    connection.commit();
                    psmt.clearBatch();
                }
            }
        } catch (SQLException e) {
            log.error("jdbc batchUpdate error, sql:{}", sql, e);
            throw new SQLException("jdbc batchUpdate error", e);
        }
    }

    @Override
    public List<Map<String, Object>> executeQuery(String sql, List params) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = getConnection(); PreparedStatement psmt = connection.prepareStatement(sql)) {
            preparedStatementResolver(psmt, params);
            rs = psmt.executeQuery();
            return resultSetResolver(rs);
        } catch (SQLException e) {
            log.error("jdbc executeQuery error, sql:{}, params:{}", sql, params, e);
            throw new SQLException("jdbc executeQuery error", e);
        } finally {
            closeResult(rs);
        }
    }

    @Override
    public <T> T executeQueryForObject(String sql, List params, Class<T> resultType) throws SQLException {
        List<Map<String, Object>> resultList = executeQuery(sql, params);
        T resultObject = null;
        try {
            if (CollectionUtils.isNotEmpty(resultList)) {
                if (resultList.size() > 1) {
                    log.warn("jdbc executeQueryForObject expect 1 but get {}", resultList.size());
                }
                Map<String, Object> rowMap = resultList.get(0);
                resultObject = mapToType(rowMap, resultType);
            }
        } catch (Exception e) {
            log.error("jdbc executeQueryForObject error, sql:{}, params:{}, resultType:{}", sql, params, resultType, e);
            throw new SQLException("jdbc executeQueryForObject error", e);
        }
        return resultObject;
    }

    @Override
    public <T> List<T> executeQueryForList(String sql, List params, Class<T> resultType) throws SQLException {
        List<Map<String, Object>> resultList = executeQuery(sql, params);
        List<T> result = Lists.newArrayList();
        try {
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (Map<String, Object> rowMap : resultList) {
                    result.add(mapToType(rowMap, resultType));
                }
            }
        } catch (Exception e) {
            log.error("jdbc executeQueryForObject error, sql:{}, params:{}, resultType:{}", sql, params, resultType, e);
            throw new SQLException("jdbc executeQueryForObject error", e);
        }
        return result;
    }

    @Override
    public Object executeCallback(CallableStatementCreator creator, CallableStatementCallback callback) throws SQLException {
        try (Connection conn = getConnection(); CallableStatement cs = creator.createCallableStatement(conn)) {
            return callback.doInCallableStatement(cs);
        } catch (SQLException e) {
            log.error("jdbc executeQuery error, sql:{}", getSql(creator), e);
            throw new SQLException("jdbc executeCallback error", e);
        }
    }

    private <T> T mapToType(Map<String, Object> rowMap, Class<T> resultType) throws SQLException, IllegalAccessException, InstantiationException {
        T resultObject;
        if (CustomUtils.isBasicType(resultType, true)) {
            if (rowMap.size() > 1) {
                log.error("jdbc executeQueryForObject can not convert result to {}", resultType.getName());
                throw new SQLException("jdbc executeQueryForObject can not convert result to" + resultType.getName());
            }
            String key = rowMap.keySet().toArray(new String[0])[0];
            resultObject = (T) rowMap.get(key);
        } else {
            resultObject = resultType.newInstance();
            Field[] fields = FieldUtils.getAllFields(resultType);
            for (Field field : fields) {
                String fieldName = field.getName();
                if (rowMap.containsKey(fieldName)) {
                    FieldUtils.writeDeclaredField(resultObject, fieldName, rowMap.get(fieldName), true);
                }
            }
        }
        return resultObject;
    }

}
