package com.aviator.mywebsite.exception;

/**
 * @Description TODO
 * @ClassName ServiceException
 * @Author aviator_ls
 * @Date 2019/4/29 14:03
 */
public class ServiceException extends MyWebSiteException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
