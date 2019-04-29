package com.aviator.mywebsite.enums;

public enum ResultEnums {

    SUCCESS("00000", "请求成功"),

    ILLEGAL_ARGUMENT("10001", "参数错误"),

    NULL_ARGUMENT("10002", "参数为空"),

    USER_USERNAME_FORMAT_ERROR("10101", "用户名长度应在6~20范围内,且只能为英文、数字、下划线"),

    USER_PASSWORD_FORMAT_ERROR("10102", "密码长度应在8~30范围内"),

    MESSAGE_CONTENT_FORMAT_ERROR("10201", "留言内容有误"),

    USER_EXIST("40001", "用户已存在"),

    USER_NOT_EXIST("40002", "用户不存在"),

    USER_NOT_LOGIN("40003", "用户未登录"),

    USER_HAS_NOT_PERMISSION("40004", "用户没有该权限"),

    USER_USERNAME_PASSWORD_ERROR("40005", "用户名或密码错误"),

    MESSAGE_NOT_EXIST("40101", "留言不存在");

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
