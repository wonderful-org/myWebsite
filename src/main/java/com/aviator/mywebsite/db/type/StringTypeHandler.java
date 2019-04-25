package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName StringTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 11:51
 */
public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, String parameter, JDBCType jdbcType) throws SQLException {
        ps.setString(column, parameter);
    }

    @Override
    String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }
}
