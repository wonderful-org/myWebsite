package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName ObjectTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 15:13
 */
public class ObjectTypeHandler extends BaseTypeHandler<Object> {

    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Object parameter, JDBCType jdbcType) throws SQLException {
        ps.setObject(column, parameter);
    }

    @Override
    Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getObject(columnIndex);
    }

    @Override
    Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName);
    }
}
