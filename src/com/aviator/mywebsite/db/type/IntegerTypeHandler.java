package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName IntegerTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 11:53
 */
public class IntegerTypeHandler extends BaseTypeHandler<Integer> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Integer parameter, JDBCType jdbcType) throws SQLException {
        ps.setInt(column, parameter);
    }

    @Override
    Integer getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int result = rs.getInt(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    Integer getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int result = rs.getInt(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }
}
