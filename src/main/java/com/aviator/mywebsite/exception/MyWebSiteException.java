package com.aviator.mywebsite.exception;

/**
 * @Description TODO
 * @ClassName MyWebSiteException
 * @Author aviator_ls
 * @Date 2019/4/25 11:22
 */
public class MyWebSiteException extends RuntimeException {

    public MyWebSiteException() {
    }

    public MyWebSiteException(String message) {
        super(message);
    }

    public MyWebSiteException(String message, Throwable cause) {
        super(message, cause);
    }
}
