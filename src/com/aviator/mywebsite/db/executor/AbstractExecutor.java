package com.aviator.mywebsite.db.executor;

import com.aviator.mywebsite.db.DBConfig;
import com.aviator.mywebsite.db.type.DefaultTypeRegistry;
import com.aviator.mywebsite.db.type.TypeHandler;
import com.aviator.mywebsite.db.type.TypeHandlerRegistry;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AbstractExecutor
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/15 20:33
 */
public abstract class AbstractExecutor implements Executor {

    private static final Logger log = LoggerFactory.getLogger(AbstractExecutor.class);

    protected DataSource dataSource;

    protected DBConfig dbConfig;

    protected TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    protected DefaultTypeRegistry typeRegistry = new DefaultTypeRegistry();

    public AbstractExecutor(DataSource dataSource, DBConfig dbConfig) {
        this.dataSource = dataSource;
        this.dbConfig = dbConfig;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected void closeResult(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    protected static String getSql(Object sqlProvider) {
        return sqlProvider instanceof SqlProvider ? ((SqlProvider) sqlProvider).getSql() : null;
    }

    protected void preparedStatementResolver(PreparedStatement ps, List params) throws SQLException {
        if (CollectionUtils.isNotEmpty(params)) {
            for (int i = 1; i <= params.size(); i++) {
                Object param = params.get(i - 1);
                Object element = param;
                JDBCType jdbcType = null;
                if (param != null && param instanceof List) {
                    List paramList = (List) param;
                    if (CollectionUtils.isNotEmpty(paramList) && paramList.get(1) instanceof JDBCType) {
                        element = paramList.get(0);
                        jdbcType = (JDBCType) paramList.get(1);
                    }
                }
                if (jdbcType == null) {
                    jdbcType = typeRegistry.getJdbcTypeByJava(element.getClass());
                }
                TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(param.getClass());
                if (typeHandler == null) {
                    log.error("can't find typeHandler, param:{}, paramType:{}", element, element.getClass());
                    throw new SQLException("can't find typeHandler");
                }
                typeHandler.setParameter(ps, i, param, jdbcType);
            }
        }
    }

    protected List<Map<String, Object>> resultSetResolver(ResultSet rs) throws SQLException {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        ResultSetMetaData metaData = rs.getMetaData();
        // 获取列数量
        int columnCount = metaData.getColumnCount();
        if (rs != null) {
            while (rs.next()) {
                Map<String, Object> rowMap = Maps.newHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    int columnType = metaData.getColumnType(i);
                    TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(JDBCType.valueOf(columnType));
                    rowMap.put(metaData.getColumnName(i), typeHandler.getResult(rs, i));
                }
                resultList.add(rowMap);
            }
        }
        return resultList;
    }

}
