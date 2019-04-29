package com.aviator.mywebsite.exception;

/**
 * @Description TODO
 * @ClassName DaoException
 * @Author aviator_ls
 * @Date 2019/4/29 19:27
 */
public class DaoException extends MyWebSiteException {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
