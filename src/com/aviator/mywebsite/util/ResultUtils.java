package com.aviator.mywebsite.util;

import com.aviator.mywebsite.constant.RespConstants;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.enums.ResultEnums;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * description: request返回内容设置工具类
 * create time: 2019/4/14 11:41
 * create by: aviator_ls
 */
public class ResultUtils {

    public static void buildSucess(ServletRequest request){
        buildSuccess(request, null);
    }

    public static void buildSuccess(ServletRequest request, Object data){
        Result result = buildResult(ResultEnums.SUCCESS, data);
        buildReq(request, result);
    }

    public static void buildFail(ServletRequest request, ResultEnums enums){
        buildFail(request, enums);
    }

    public static void buildFail(ServletRequest request, ResultEnums enums, Object data){
        Result result = buildResult(enums, data);
        buildReq(request, result);
    }

    private static void buildReq(ServletRequest request, Result result) {
        request.setAttribute(RespConstants.ResultResp.CODE, result.getCode());
        request.setAttribute(RespConstants.ResultResp.MSG, result.getMsg());
        request.setAttribute(RespConstants.ResultResp.DATA, result.getData());
    }

    private static Result buildResult(ResultEnums enums){
        return buildResult(enums);
    }

    private static Result buildResult(ResultEnums enums, Object data){
        return new Result(enums.getCode(), enums.getMsg(), data);
    }
}
