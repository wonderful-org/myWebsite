package com.aviator.mywebsite.db.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description TODO
 * @ClassName CharacterTypeHandler
 * @Author aviator_ls
 * @Date 2019/4/24 14:41
 */
public class CharacterTypeHandler extends BaseTypeHandler<Character> {
    @Override
    void setNonNullParameter(PreparedStatement ps, int column, Character parameter, JDBCType jdbcType) throws SQLException {
        ps.setString(column, parameter.toString());
    }

    @Override
    Character getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return result == null ? null : result.charAt(0);
    }

    @Override
    Character getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        return result == null ? null : result.charAt(0);
    }
}
