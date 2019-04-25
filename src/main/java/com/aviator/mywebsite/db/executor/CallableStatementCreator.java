package com.aviator.mywebsite.db.executor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName CallableStatementCreator
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/24 10:44
 */
public interface CallableStatementCreator {

    CallableStatement createCallableStatement(Connection con) throws SQLException;
}
