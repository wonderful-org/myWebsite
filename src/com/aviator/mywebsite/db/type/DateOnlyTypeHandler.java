package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Description TODO
 * @ClassName DateOnlyTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 12:06
 */
public class DateOnlyTypeHandler extends BaseTypeHandler<Date> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Date parameter, JDBCType jdbcType) throws SQLException {
        ps.setDate(column, new java.sql.Date(parameter.getTime()));
    }

    @Override
    Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        java.sql.Date sqlDate = rs.getDate(columnIndex);
        return sqlDate == null ? null : new Date(sqlDate.getTime());
    }

    @Override
    Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        java.sql.Date sqlDate = rs.getDate(columnName);
        return sqlDate == null ? null : new Date(sqlDate.getTime());
    }
}
