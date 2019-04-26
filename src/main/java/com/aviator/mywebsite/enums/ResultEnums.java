package com.aviator.mywebsite.enums;

public enum ResultEnums {

    SUCCESS("0000", "请求成功"),

    ILLEGAL_ARGUMENT("1001", "参数错误"),

    NULL_ARGUMENT("1002", "参数为空"),

    USER_EXIST("4001", "用户已存在"),

    USER_NOT_EXIST("4002", "用户不存在"),

    USERNAME_PASSWORD_ERROR("4003", "用户名或密码错误");

    private String code;

    private String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
