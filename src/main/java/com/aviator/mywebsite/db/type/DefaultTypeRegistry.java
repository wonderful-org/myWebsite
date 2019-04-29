package com.aviator.mywebsite.db.type;

import java.lang.reflect.Type;
import java.sql.JDBCType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName DefaultTypeRegistry
 * @Author aviator_ls
 * @Date 2019/4/24 17:18
 */
public class DefaultTypeRegistry {

    private final Map<Type, JDBCType> typeMapping = new HashMap<>();

    private static DefaultTypeRegistry instance = new DefaultTypeRegistry();

    public static DefaultTypeRegistry getInstance(){
        return instance;
    }

    private DefaultTypeRegistry() {
        register(String.class, JDBCType.VARCHAR);

        register(Boolean.class, JDBCType.BOOLEAN);
        register(boolean.class, JDBCType.BOOLEAN);

        register(Character.class, JDBCType.CHAR);
        register(char.class, JDBCType.CHAR);

        register(Short.class, JDBCType.SMALLINT);
        register(short.class, JDBCType.SMALLINT);

        register(Integer.class, JDBCType.INTEGER);
        register(int.class, JDBCType.INTEGER);

        register(Long.class, JDBCType.BIGINT);
        register(long.class, JDBCType.BIGINT);

        register(Float.class, JDBCType.FLOAT);
        register(float.class, JDBCType.FLOAT);

        register(Double.class, JDBCType.DOUBLE);
        register(double.class, JDBCType.DOUBLE);

        register(Date.class, JDBCType.TIMESTAMP);
    }

    public synchronized void register(Type javaType, JDBCType jdbcType) {
        typeMapping.put(javaType, jdbcType);
    }

    public JDBCType getJdbcTypeByJava(Type javaType) {
        JDBCType jdbcType = typeMapping.get(javaType);
        if (jdbcType == null) {
            jdbcType = JDBCType.OTHER;
        }
        return jdbcType;
    }
}
