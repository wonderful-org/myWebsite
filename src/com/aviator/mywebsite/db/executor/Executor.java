package com.aviator.mywebsite.db.executor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Executor
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/19 12:54
 */
public interface Executor {

    int executeInsert(String sql, List params) throws SQLException;

    <T> T executeInsert(String sql, List params, Class<T> idType) throws SQLException;

    int executeUpdate(String sql, List params) throws SQLException;

    void batchUpdate(String sql, List<List> paramss) throws SQLException;

    List<Map<String, Object>> executeQuery(String sql, List params) throws SQLException;

    <T> T executeQueryForObject(String sql, List params, Class<T> resultType) throws SQLException;

    <T> List<T> executeQueryForList(String sql, List params, Class<T> resultType) throws SQLException;

    Object executeCallback(CallableStatementCreator creator, CallableStatementCallback callback) throws SQLException;
}
