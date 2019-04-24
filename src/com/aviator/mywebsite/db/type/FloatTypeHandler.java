package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName FloatTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:46
 */
public class FloatTypeHandler extends BaseTypeHandler<Float> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Float parameter, JDBCType jdbcType) throws SQLException {
        ps.setFloat(column, parameter);
    }

    @Override
    Float getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        float result = rs.getFloat(columnIndex);
        return result == 0f && rs.wasNull() ? null : result;
    }

    @Override
    Float getNullableResult(ResultSet rs, String columnName) throws SQLException {
        float result = rs.getFloat(columnName);
        return result == 0f && rs.wasNull() ? null : result;
    }
}
