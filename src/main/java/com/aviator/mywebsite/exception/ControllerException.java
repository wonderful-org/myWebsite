package com.aviator.mywebsite.exception;

/**
 * @Description TODO
 * @ClassName ControllerException
 * @Author aviator_ls
 * @Date 2019/4/26 10:14
 */
public class ControllerException extends MyWebSiteException {

    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
