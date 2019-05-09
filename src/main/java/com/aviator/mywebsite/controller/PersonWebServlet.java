package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @ClassName PersonWebServlet
 * @Author aviator_ls
 * @Date 2019/5/9 23:33
 */
@WebServlet("/personWeb/*")
public class PersonWebServlet extends BaseServlet {

    @GetMapping("/toPersonWeb")
    public String toPersonWeb(HttpServletRequest req) {
        if (!SecurityUtils.isLogin(req)) {
            ResultUtils.buildFail(req, ResultEnums.USER_NOT_LOGIN);
            return "r:/user/toLogin";
        }
        UserResp userResp = SecurityUtils.getCurrentUser(req);
        Result result = userService.getUserInfoByUserId(userResp.getId());
        if (result.isSuccess()) {
            ResultUtils.buildSuccess(req, result);
        } else {
            ResultUtils.buildFail(req, result);
        }
        return "person_web";
    }
}
