package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.annotation.PostMapping;
import com.aviator.mywebsite.annotation.RequestParam;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.UserReq;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
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
    public String toLogin(HttpServletRequest req, @RequestParam("curUrl") String curUrl) {
        ResultUtils.buildSuccess(req, curUrl);
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, UserReq userReq, @RequestParam("curUrl") String curUrl) {
        Result result = userService.login(userReq);
        if (result.isSuccess()) {
            curUrl = getCurUri(req, curUrl);
            SecurityUtils.setCurrentUser(req, (UserResp) result.getData());
            return StringUtils.isBlank(curUrl) ? "index" : "r:/" + curUrl;
        }
        ResultUtils.buildFail(req, result);
        return "login";
    }

    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest req, UserReq userReq) {
        Result result = userService.register(userReq);
        if (result.isSuccess()) {
            return "f:login";
        }
        ResultUtils.buildFail(req, result);
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, @RequestParam("curUrl") String curUrl) {
        req.getSession().removeAttribute(SecurityUtils.USER_SESSION_ATTRIBUTE);
        curUrl = getCurUri(req, curUrl);
        return StringUtils.isBlank(curUrl) ? "index" : "r:/" + curUrl;
    }

    @GetMapping("/personCenter")
    public String personCenter(HttpServletRequest req) {
        if (!SecurityUtils.isLogin(req)) {
            ResultUtils.buildFail(req, ResultEnums.USER_NOT_LOGIN);
        }
        return "person_center";
    }

    private String getCurUri(HttpServletRequest req, String curUrl) {
        if (StringUtils.isNotBlank(curUrl)) {
            curUrl = StringUtils.substringAfterLast(curUrl, req.getContextPath());
            if (StringUtils.isNotBlank(curUrl)) {
                while (curUrl.charAt(0) == '/') {
                    curUrl = StringUtils.substringAfter(curUrl, "/");
                }
            }
        }
        return curUrl;
    }

}
