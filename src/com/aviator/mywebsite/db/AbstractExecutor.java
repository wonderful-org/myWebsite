package com.aviator.mywebsite.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName AbstractExecutor
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/15 20:33
 */
public abstract class AbstractExecutor {

    private Executor executor;

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
