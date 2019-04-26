package com.aviator.mywebsite.util;

import com.aviator.mywebsite.constant.RespConstants;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.enums.ResultEnums;

import javax.servlet.ServletRequest;

/**
 * description: request返回内容设置工具类
 * create time: 2019/4/14 11:41
 * create by: aviator_ls
 */
public class ResultUtils {

    public static void buildSuccess(ServletRequest request) {
        buildSuccess(request, null);
    }

    public static void buildSuccess(ServletRequest request, Object data) {
        Result result = buildResult(ResultEnums.SUCCESS, data);
        buildReq(request, result);
    }

    public static void buildSuccess(ServletRequest request, Result result) {
        buildReq(request, result);
    }

    public static void buildFail(ServletRequest request, ResultEnums enums) {
        buildFail(request, enums);
    }

    public static void buildFail(ServletRequest request, ResultEnums enums, Object data) {
        Result result = buildResult(enums, data);
        buildReq(request, result);
    }

    public static void buildFail(ServletRequest request, Result result) {
        buildReq(request, result);
    }

    public static Result buildResult(ResultEnums enums) {
        return buildResult(enums, null);
    }

    public static Result buildResult(ResultEnums enums, Object data) {
        return new Result(enums.getCode(), enums.getMsg(), data);
    }

    private static void buildReq(ServletRequest request, Result result) {
        request.setAttribute(RespConstants.ResultResp.CODE, result.getCode());
        request.setAttribute(RespConstants.ResultResp.MSG, result.getMsg());
        request.setAttribute(RespConstants.ResultResp.DATA, result.getData());
        if (!ResultEnums.SUCCESS.getCode().equals(result.getCode())) {
            request.setAttribute(RespConstants.ResultResp.ERR_MSG, result.getMsg());
        }
    }
}
