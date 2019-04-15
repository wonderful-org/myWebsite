package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.constant.ReqConstants;
import com.aviator.mywebsite.entity.BaseEntity;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(TestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("test request url: {}", req.getRequestURL().toString());
        log.info("param: {}", req.getParameter(ReqConstants.TestReq.STRING_NAME));
        ResultUtils.buildSuccess(req, req.getParameter(ReqConstants.TestReq.STRING_NAME));
        req.getRequestDispatcher("/test.jsp").forward(req, resp);
    }
}
