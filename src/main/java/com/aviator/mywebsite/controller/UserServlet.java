package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.annotation.PostMapping;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.UserReq;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;
import com.aviator.mywebsite.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @ClassName UserServlet
 * @Author aviator_ls
 * @Date 2019/4/25 22:15
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req) {
        UserReq userReq = ServletUtils.getParams(req, UserReq.class);
        Result result = userService.login(userReq);
        if (result.isSuccess()) {
            req.getSession().setAttribute(SecurityUtils.USER_SESSION_ATTRIBUTE, result.getData());
            return "index";
        }
        ResultUtils.buildFail(req, result);
        return "login";
    }

    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest req) {
        UserReq userReq = ServletUtils.getParams(req, UserReq.class);
        Result result = userService.register(userReq);
        if (result.isSuccess()) {
            req.getSession().setAttribute(SecurityUtils.USER_SESSION_ATTRIBUTE, result.getData());
            return "f:login";
        }
        ResultUtils.buildFail(req, result);
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        req.getSession().removeAttribute(SecurityUtils.USER_SESSION_ATTRIBUTE);
        return "index";
    }

}
