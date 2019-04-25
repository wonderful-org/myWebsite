package com.aviator.mywebsite.db.datasource;


import com.aviator.mywebsite.db.DataBaseException;

/**
 * @ClassName DataSourceException
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/17 10:22
 */
public class DataSourceException extends DataBaseException {

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
