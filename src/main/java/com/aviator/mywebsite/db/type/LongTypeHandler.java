package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName LongTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:45
 */
public class LongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Long parameter, JDBCType jdbcType) throws SQLException {
        ps.setLong(column, parameter);
    }

    @Override
    Long getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        long result = rs.getLong(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    Long getNullableResult(ResultSet rs, String columnName) throws SQLException {
        long result = rs.getLong(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }
}
