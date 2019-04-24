package com.aviator.mywebsite.db.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName BaseTypeHandler
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/24 11:16
 */
public abstract class BaseTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {

    private static final Logger log = LoggerFactory.getLogger(BaseTypeHandler.class);

    @Override
    public void setParameter(PreparedStatement ps, int column, T parameter, JDBCType jdbcType) {
        if (parameter == null) {
            if (jdbcType == null) {
                log.error("jdbc requires jdbcType");
                throw new TypeException("jdbc requires jdbcType");
            }
            try {
                ps.setNull(column, jdbcType.getVendorTypeNumber());
            } catch (SQLException e) {
                log.error("PreparedStatement setNull error, column:{}, jdbcType code:{}", column, jdbcType.getVendorTypeNumber(), e);
                throw new TypeException("PreparedStatement setNull error", e);
            }
        } else {
            try {
                setNonNullParameter(ps, column, parameter, jdbcType);
            } catch (Exception e) {
                log.error("setNonNullParameter error, column:{}, parameter:{}, jdbcType:{}", column, parameter, jdbcType);
                throw new TypeException("setNonNullParameter error", e);
            }
        }
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) {
        try {
            return getNullableResult(rs, columnIndex);
        } catch (Exception e) {
            log.error("getNullableResult error, columnIndex:{}", columnIndex, e);
            throw new TypeException("getNullableResult error", e);
        }
    }

    @Override
    public T getResult(ResultSet rs, String columnName) {
        try {
            return getNullableResult(rs, columnName);
        } catch (Exception e) {
            log.error("getNullableResult error, columnName:{}", columnName, e);
            throw new TypeException("getNullableResult error", e);
        }
    }

    abstract void setNonNullParameter(PreparedStatement ps, int column, T parameter, JDBCType jdbcType) throws SQLException;

    abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

    abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;
}
