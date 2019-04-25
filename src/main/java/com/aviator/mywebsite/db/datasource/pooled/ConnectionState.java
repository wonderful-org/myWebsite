package com.aviator.mywebsite.db.datasource.pooled;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName ConnectionState
 * @Author aviator_ls
 * @Date 2019/4/25 10:47
 */
public class ConnectionState {
    private boolean autoCommit;
    private int transactionIsolation;

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public int getTransactionIsolation() {
        return transactionIsolation;
    }

    public void setTransactionIsolation(int transactionIsolation) {
        this.transactionIsolation = transactionIsolation;
    }

    public void resetConnection(Connection conn) throws SQLException {
        conn.setAutoCommit(autoCommit);
        conn.setTransactionIsolation(transactionIsolation);
    }
}
