package com.aviator.mywebsite.db.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description 引用泛型抽象类，获取类泛型
 * @ClassName TypeReference
 * @Author aviator_ls
 * @Date 2019/4/24 11:18
 */
public abstract class TypeReference<T> {
    private final Type rawType;

    protected TypeReference() {
        rawType = getSuperclassTypeParameter(getClass());
    }

    Type getSuperclassTypeParameter(Class<?> clazz) {
        // 从父类获取T
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            // try to climb up the hierarchy until meet something useful
            // 排除TypeReference
            if (TypeReference.class != genericSuperclass) {
                return getSuperclassTypeParameter(clazz.getSuperclass());
            }

            throw new TypeException("'" + getClass() + "' extends TypeReference but misses the type parameter. "
                    + "Remove the extension or add a type parameter to it.");
        }
        // 获取T
        Type rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        // TODO remove this when Reflector is fixed to return Types
        // 必须是参数化泛型，才获取T
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }

        return rawType;
    }

    public final Type getRawType() {
        return rawType;
    }

    @Override
    public String toString() {
        return rawType.toString();
    }

}
