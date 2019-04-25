package com.aviator.mywebsite.db.type;

import java.sql.*;
import java.util.Date;

/**
 * @Description TODO
 * @ClassName TimeOnlyTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:32
 */
public class TimeOnlyTypeHandler extends BaseTypeHandler<Date> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Date parameter, JDBCType jdbcType) throws SQLException {
        ps.setTime(column, new Time(parameter.getTime()));
    }

    @Override
    Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Time time = rs.getTime(columnIndex);
        return time == null ? null : new Date(time.getTime());
    }

    @Override
    Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Time time = rs.getTime(columnName);
        return time == null ? null : new Date(time.getTime());
    }
}
