package com.aviator.mywebsite.db;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TestJdbcUtils
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/19 19:57
 */
public final class TestJdbcUtils {

    private static final Logger log = LoggerFactory.getLogger(TestJdbcUtils.class);

    public static void main(String[] args) {
        executeInsert();
        executeInsertIdType();
        executeQueryForList();
        executeUpdate();
        batchUpdate();
        executeQueryForList();
        executeUpdate();
    }

    private static void executeInsert() {
        String sql = "insert into t_test (string_field, int_field, bigint_field, datetime_field) values (?,?,?,?)";
        List list = Lists.newArrayList();
        list.add("testStr");
        list.add(1);
        list.add(1);
        list.add(new Date(System.currentTimeMillis()));
        int result = JdbcUtils.executeInsert(sql, list);
        log.info("executeInsert result: " + result);
    }

    private static void executeInsertIdType() {
        String sql = "insert into t_test (string_field, int_field, bigint_field, datetime_field) values (?,?,?,?)";
        List list = Lists.newArrayList();
        list.add("testStr");
        list.add(1);
        list.add(1);
        list.add(new Date(System.currentTimeMillis()));
        long result = JdbcUtils.executeInsert(sql, list, Long.class);
        log.info("executeInsertIdType result: " + result);
    }

    private static void executeUpdate() {
        String sql = "delete from t_test";
        int result = JdbcUtils.executeUpdate(sql);
        log.info("executeUpdate result: " + result);
    }

    private static void batchUpdate() {
        String sql = "insert into t_test (string_field, int_field, bigint_field, datetime_field) values (?,?,?,?)";
        List<List> lists = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            List list = Lists.newArrayList();
            list.add("testStr");
            list.add(1);
            list.add(1);
            list.add(new Date(System.currentTimeMillis()));
            lists.add(list);
        }
        JdbcUtils.batchUpdate(sql, lists);
    }

    private static void executeQueryForList() {
        String sql = "select * from t_test";
        List<TTest> result = JdbcUtils.executeQueryForList(sql, TTest.class);
        log.info("executeQueryForList result: " + result);
    }

}