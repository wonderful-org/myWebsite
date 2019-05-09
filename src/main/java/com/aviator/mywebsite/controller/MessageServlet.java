package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.annotation.PostMapping;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.MessageReq;
import com.aviator.mywebsite.entity.dto.req.PageReq;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @ClassName MessageServlet
 * @Author aviator_ls
 * @Date 2019/4/28 18:13
 */
@WebServlet("/message/*")
public class MessageServlet extends BaseServlet {

    @GetMapping("/toMessages")
    public String toMessages() {
        return "f:messages";
    }

    @GetMapping("/messages")
    public String messages(HttpServletRequest req, PageReq pageReq) {
        Result result = messageService.findMessagePage(pageReq);
        if (result.isSuccess()) {
            ResultUtils.buildSuccess(req, result);
        } else {
            ResultUtils.buildFail(req, result);
        }
        return "messages";
    }

    @PostMapping("")
    public String add(HttpServletRequest request, MessageReq messageReq) {
        if (SecurityUtils.isLogin(request)) {
            UserResp user = SecurityUtils.getCurrentUser(request);
            messageService.insertMessage(user.getId(), messageReq);
        } else {
            PageReq pageReq = new PageReq();
            pageReq.setPageNum(1);
            Result result = messageService.findMessagePage(pageReq);
            ResultUtils.buildFail(request, ResultEnums.USER_NOT_LOGIN, result.getData());
            return "messages";
        }
        return "r:messages";
    }

}
