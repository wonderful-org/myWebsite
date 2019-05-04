package com.aviator.mywebsite.util;

/**
 * @Description TODO
 * @ClassName CustomUtils
 * @Author aviator_ls
 * @Date 2019/5/3 20:39
 */
public class CustomUtils {

    public static boolean isBasicType(Class className) {
        return isBasicType(className, true);
    }

    public static boolean isBasicType(Class className, boolean incString) {
        if (incString && className.equals(String.class)) {
            return true;
        }
        return className.equals(Integer.class) ||
                className.equals(int.class) ||
                className.equals(Byte.class) ||
                className.equals(byte.class) ||
                className.equals(Long.class) ||
                className.equals(long.class) ||
                className.equals(Double.class) ||
                className.equals(double.class) ||
                className.equals(Float.class) ||
                className.equals(float.class) ||
                className.equals(Character.class) ||
                className.equals(char.class) ||
                className.equals(Short.class) ||
                className.equals(short.class) ||
                className.equals(Boolean.class) ||
                className.equals(boolean.class);
    }
}
