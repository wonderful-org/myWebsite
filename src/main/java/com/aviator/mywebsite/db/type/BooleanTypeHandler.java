package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName BooleanTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:37
 */
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Boolean parameter, JDBCType jdbcType) throws SQLException {
        ps.setBoolean(column, parameter);
    }

    @Override
    Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Boolean result = rs.getBoolean(columnIndex);
        return !result && rs.wasNull() ? null : result;
    }

    @Override
    Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Boolean result = rs.getBoolean(columnName);
        return !result && rs.wasNull() ? null : result;
    }
}
