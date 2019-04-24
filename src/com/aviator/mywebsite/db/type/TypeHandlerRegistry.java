package com.aviator.mywebsite.db.type;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.sql.JDBCType;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName TypeHandlerRegistry
 * @Author aviator_ls
 * @Date 2019/4/24 15:15
 */
public class TypeHandlerRegistry {

    private static final Logger log = LoggerFactory.getLogger(TypeHandlerRegistry.class);

    private final Map<JDBCType, TypeHandler<?>> jdbcTypeHandlerMap = Maps.newEnumMap(JDBCType.class);

    private final Map<Type, Map<JDBCType, TypeHandler<?>>> typeHandlerMap = Maps.newConcurrentMap();

    private final Map<JDBCType, TypeHandler<?>> NULL_TYPE_HANDLER_MAP = Collections.emptyMap();

    private final TypeHandler<Object> unknownTypeHandler = new ObjectTypeHandler();

    public TypeHandlerRegistry() {
        register(Boolean.class, new BooleanTypeHandler());
        register(boolean.class, new BooleanTypeHandler());
        register(JDBCType.BOOLEAN, new BooleanTypeHandler());

        register(Byte.class, new ByteTypeHandler());
        register(byte.class, new ByteTypeHandler());

        register(Character.class, new CharacterTypeHandler());
        register(char.class, new CharacterTypeHandler());

        register(Integer.class, new IntegerTypeHandler());
        register(int.class, new IntegerTypeHandler());
        register(JDBCType.INTEGER, new IntegerTypeHandler());
        register(JDBCType.TINYINT, new IntegerTypeHandler());

        register(Long.class, new LongTypeHandler());
        register(long.class, new LongTypeHandler());
        register(JDBCType.BIGINT, new LongTypeHandler());

        register(Short.class, new ShortTypeHandler());
        register(short.class, new ShortTypeHandler());
        register(JDBCType.SMALLINT, new ShortTypeHandler());

        register(Float.class, new FloatTypeHandler());
        register(float.class, new FloatTypeHandler());
        register(JDBCType.FLOAT, new FloatTypeHandler());

        register(Double.class, new DoubleTypeHandler());
        register(double.class, new DoubleTypeHandler());
        register(JDBCType.DOUBLE, new DoubleTypeHandler());

        register(Date.class, new DateTypeHandler());
        register(Date.class, JDBCType.TIME, new TimeOnlyTypeHandler());
        register(Date.class, JDBCType.DATE, new DateOnlyTypeHandler());
        register(Date.class, JDBCType.TIMESTAMP, new DateTypeHandler());
        register(JDBCType.TIMESTAMP, new DateTypeHandler());
        register(JDBCType.DATE, new DateOnlyTypeHandler());
        register(JDBCType.TIME, new TimeOnlyTypeHandler());

        register(String.class, new StringTypeHandler());
        register(String.class, JDBCType.CHAR, new StringTypeHandler());
        register(String.class, JDBCType.VARCHAR, new StringTypeHandler());
        register(String.class, JDBCType.LONGVARCHAR, new StringTypeHandler());
        register(JDBCType.CHAR, new StringTypeHandler());
        register(JDBCType.VARCHAR, new StringTypeHandler());
        register(JDBCType.LONGVARCHAR, new StringTypeHandler());


        register(Object.class, unknownTypeHandler);
        register(Object.class, JDBCType.OTHER, unknownTypeHandler);
        register(JDBCType.OTHER, unknownTypeHandler);
    }

    public <T> TypeHandler<T> getTypeHandler(Type javaType, JDBCType jdbcType) {
        Map<JDBCType, TypeHandler<?>> jdbcHandlerMap = typeHandlerMap.get(javaType);
        if (NULL_TYPE_HANDLER_MAP.equals(jdbcHandlerMap)) {
            return null;
        }
        typeHandlerMap.put(javaType, jdbcHandlerMap == null ? NULL_TYPE_HANDLER_MAP : jdbcHandlerMap);
        if (jdbcHandlerMap == null) {
            return null;
        }
        TypeHandler typeHandler = jdbcHandlerMap.get(jdbcType);
        if (typeHandler == null) {
            typeHandler = jdbcHandlerMap.get(null);
        }
        if (typeHandler == null) {
            for (TypeHandler<?> handler : jdbcHandlerMap.values()) {
                if (typeHandler == null) {
                    typeHandler = handler;
                } else if (!handler.getClass().equals(typeHandler.getClass())) {
                    // More than one type handlers registered.
                    typeHandler = null;
                    break;
                }
            }
        }
        return typeHandler;
    }

    public <T> TypeHandler<T> getTypeHandler(Type javaType) {
        return getTypeHandler(javaType, null);
    }

    public TypeHandler<?> getTypeHandler(JDBCType jdbcType) {
        return jdbcTypeHandlerMap.get(jdbcType);
    }

    public void register(Type javaType, TypeHandler handler) {
        register(javaType, null, handler);
    }

    public void register(Type javaType, JDBCType jdbcType, TypeHandler handler) {
        Map<JDBCType, TypeHandler<?>> jdbcHandlerMap = typeHandlerMap.get(javaType);
        if (jdbcHandlerMap == null || jdbcHandlerMap.equals(NULL_TYPE_HANDLER_MAP)) {
            jdbcHandlerMap = Maps.newHashMap();
            typeHandlerMap.put(javaType, jdbcHandlerMap);
        }
        jdbcHandlerMap.put(jdbcType, handler);
    }

    public void register(JDBCType type, TypeHandler handler) {
        jdbcTypeHandlerMap.put(type, handler);
    }

}
