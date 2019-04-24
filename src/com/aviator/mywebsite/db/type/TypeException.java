package com.aviator.mywebsite.db.type;

import com.aviator.mywebsite.db.DataBaseException;

/**
 * @ClassName TypeException
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/24 11:19
 */
public class TypeException extends DataBaseException {
    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
