package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface TypeHandler<T> {

    void setParameter(PreparedStatement ps, int column, T parameter, JDBCType jdbcType);

    T getResult(ResultSet rs, int columnIndex);

    T getResult(ResultSet rs, String columnName);
}
