package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName DoubleTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:48
 */
public class DoubleTypeHandler extends BaseTypeHandler<Double> {

    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Double parameter, JDBCType jdbcType) throws SQLException {
        ps.setDouble(column, parameter);
    }

    @Override
    Double getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        double result = rs.getDouble(columnIndex);
        return result == 0d && rs.wasNull() ? null : result;
    }

    @Override
    Double getNullableResult(ResultSet rs, String columnName) throws SQLException {
        double result = rs.getDouble(columnName);
        return result == 0d && rs.wasNull() ? null : result;
    }
}
