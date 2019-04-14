package com.aviator.mywebsite.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResumeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从request中获取参数
        String age = req.getParameter("age");
        // 设置requestAttribute
        req.setAttribute("age", age);
        req.getRequestDispatcher("resume.jsp").forward(req, resp);
    }
}
