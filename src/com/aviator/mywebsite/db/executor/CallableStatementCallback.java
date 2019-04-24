package com.aviator.mywebsite.db.executor;

import java.sql.CallableStatement;

/**
 * @ClassName CallableStatementCallback
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/24 10:31
 */
public interface CallableStatementCallback {

    Object doInCallableStatement(CallableStatement cs);
}
