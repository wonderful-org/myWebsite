package com.aviator.mywebsite.entity;

import com.aviator.mywebsite.enums.ResultEnums;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName Result
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/14 11:46
 */
public class Result extends BaseEntity {

    private String code;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return StringUtils.isNotBlank(code) && code.equals(ResultEnums.SUCCESS.getCode());
    }
}
