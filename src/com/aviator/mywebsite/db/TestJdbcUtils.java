package com.aviator.mywebsite.db;

import java.sql.*;

/**
 * @ClassName TestJdbcUtils
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/15 18:45
 */
public class TestJdbcUtils {

    private static DBConfig config;

    static {
        // 读数据
        config = new DBConfig();
        config.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        config.setUserName("root");
        config.setPassword("123456");
        config.setDriverClass("com.mysql.cj.jdbc.Driver");
        try {
            Class.forName(config.getDriverClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        insert();
        batchUpdate();
        query();
    }

    private static void insert() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.execute("insert into t_test (string_field) values ('测试字符串')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    private static void query() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from t_test");
            while (resultSet.next()) {
                System.out.println(resultSet.getObject(1));
                System.out.println(resultSet.getObject(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, resultSet);
        }
    }

    private static void batchUpdate() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (int i = 0; i < 10; i++) {
                statement.addBatch("insert into t_test (int_field) values (" + i + ")");
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getUrl(), config.getUserName(), config.getPassword());
    }

    private static void close(Connection connection, Statement statement) {
        close(connection, statement, null);
    }

    private static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
