package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.constant.ReqConstants;
import com.aviator.mywebsite.entity.BaseEntity;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: servlet测试类，完成从前台获取参数，并且设置request参数，渲染jsp页面并返回
 * create time: 2019/4/14 11:41
 * create by: aviator_ls
 */
public class TestServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultUtils.buildSuccess(req, req.getParameter(ReqConstants.TestReq.STRING_NAME));
        req.getRequestDispatcher("/test.jsp").forward(req, resp);
    }
}
