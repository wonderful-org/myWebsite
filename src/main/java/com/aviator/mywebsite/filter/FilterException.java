package com.aviator.mywebsite.filter;

import com.aviator.mywebsite.exception.MyWebSiteException;

/**
 * @Description TODO
 * @ClassName FilterException
 * @Author aviator_ls
 * @Date 2019/4/25 11:23
 */
public class FilterException extends MyWebSiteException {
    public FilterException(String message) {
        super(message);
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }
}
