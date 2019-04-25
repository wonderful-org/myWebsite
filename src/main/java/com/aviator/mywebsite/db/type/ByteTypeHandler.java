package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName ByteTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:39
 */
public class ByteTypeHandler extends BaseTypeHandler<Byte> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Byte parameter, JDBCType jdbcType) throws SQLException {
        ps.setByte(column, parameter);
    }

    @Override
    Byte getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Byte result = rs.getByte(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    Byte getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Byte result = rs.getByte(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }
}
