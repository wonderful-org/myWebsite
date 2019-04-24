package com.aviator.mywebsite.db.type;

import java.sql.*;
import java.util.Date;

/**
 * @Description TODO
 * @ClassName DateTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 12:03
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Date parameter, JDBCType jdbcType) throws SQLException {
        ps.setTimestamp(column, new Timestamp(parameter.getTime()));
    }

    @Override
    Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return timestamp == null ? null : new Date(timestamp.getTime());
    }

    @Override
    Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp == null ? null : new Date(timestamp.getTime());
    }
}
