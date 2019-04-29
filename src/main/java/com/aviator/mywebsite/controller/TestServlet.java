package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.*;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.UserReq;
import com.aviator.mywebsite.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * description: servlet测试类，完成从前台获取参数，并且设置request参数，渲染jsp页面并返回
 * create time: 2019/4/14 11:41
 * create by: aviator_ls
 */
@WebServlet({"/test/*", "/test"})
public class TestServlet extends BaseServlet {

    private static final Logger log = LoggerFactory.getLogger(TestServlet.class);

    @GetMapping("/f")
    public String forward() {
        return "f:paramName";
    }

    @GetMapping("/paramName")
    public String paramName(@RequestParam("name") String name, HttpServletRequest req) {
        ResultUtils.buildSuccess(req, name);
        return "test";
    }

    @GetMapping("/userJsonGet")
    @ResponseBody
    public Result userJsonGet(@RequestBody UserReq user) {
        return ResultUtils.buildSuccessResult(user);
    }

    @PostMapping("/userJsonPost")
    @ResponseBody
    public Result userJsonPost(@RequestBody UserReq user) {
        return ResultUtils.buildSuccessResult(user);
    }

}
