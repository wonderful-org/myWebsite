package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName ShortTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:44
 */
public class ShortTypeHandler extends BaseTypeHandler<Short> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Short parameter, JDBCType jdbcType) throws SQLException {
        ps.setShort(column, parameter);
    }

    @Override
    Short getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        short result = rs.getShort(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    Short getNullableResult(ResultSet rs, String columnName) throws SQLException {
        short result = rs.getShort(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }
}
