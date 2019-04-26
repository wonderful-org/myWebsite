package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;

/**
 * description: servlet测试类，完成从前台获取参数，并且设置request参数，渲染jsp页面并返回
 * create time: 2019/4/14 11:41
 * create by: aviator_ls
 */
@WebServlet("/test/*")
public class TestServlet extends BaseServlet {

    private static final Logger log = LoggerFactory.getLogger(TestServlet.class);

    @GetMapping("/a")
    public String a() {
        return "f:b";
    }

    @GetMapping("/b")
    public String b() {
        return "test";
    }
}
