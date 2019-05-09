package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.annotation.PostMapping;
import com.aviator.mywebsite.annotation.RequestParam;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.FolderReq;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @ClassName FolderServlet
 * @Author aviator_ls
 * @Date 2019/5/9 21:20
 */
@WebServlet("/folder/*")
public class FolderServlet extends BaseServlet {

    @PostMapping("/add")
    public String add(HttpServletRequest req, FolderReq folderReq) {
        if (!SecurityUtils.isLogin(req)) {
            ResultUtils.buildFail(req, ResultEnums.USER_NOT_LOGIN);
            return "r:/user/toLogin";
        }
        UserResp userResp = SecurityUtils.getCurrentUser(req);
        folderReq.setAuthorId(userResp.getId());
        Result result = folderService.insertFolder(folderReq);
        if (result.isSuccess()) {
            ResultUtils.buildSuccess(req, result);
            return "r:/personCenter/blog?folderId=" + folderReq.getParentFolderId();
        } else {
            ResultUtils.buildFail(req, result);
        }
        return "r:/personCenter/blog";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest req, FolderReq folderReq) {
        if (!SecurityUtils.isLogin(req)) {
            ResultUtils.buildFail(req, ResultEnums.USER_NOT_LOGIN);
            return "r:/user/toLogin";
        }
        UserResp userResp = SecurityUtils.getCurrentUser(req);
        folderReq.setAuthorId(userResp.getId());
        Result result = folderService.updateFolder(folderReq);
        if (result.isSuccess()) {
            ResultUtils.buildSuccess(req, result);
        } else {
            ResultUtils.buildFail(req, result);
        }
        return "r:/personCenter/blog?folderId=" + folderReq.getId();
    }

    @GetMapping("/delete")
    public Result delete(HttpServletRequest req, @RequestParam("id") long id) {
        if (!SecurityUtils.isLogin(req)) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_LOGIN);
        }
        Result result = folderService.deleteFolder(req, id);
        return result;
    }
}
