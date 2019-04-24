package com.aviator.mywebsite.db;

/**
 * @ClassName DataBaseException
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/19 11:54
 */
public class DataBaseException extends RuntimeException{

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
