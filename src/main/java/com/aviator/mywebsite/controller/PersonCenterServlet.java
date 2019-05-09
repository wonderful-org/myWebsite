package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.annotation.PostMapping;
import com.aviator.mywebsite.annotation.RequestParam;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.UserInfoReq;
import com.aviator.mywebsite.entity.dto.resp.FolderResp;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName PersonCenterServlet
 * @Author aviator_ls
 * @Date 2019/5/9 18:31
 */
@WebServlet("/personCenter/*")
public class PersonCenterServlet extends BaseServlet {

    @GetMapping("/userInfo")
    public String userInfo(HttpServletRequest req) {
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
        return "personCenter/person_info";
    }

    @PostMapping("/addUserInfo")
    public String addUserInfo(HttpServletRequest req, UserInfoReq userInfoReq) {
        if (!SecurityUtils.isLogin(req)) {
            ResultUtils.buildFail(req, ResultEnums.USER_NOT_LOGIN);
            return "r:/user/toLogin";
        }
        UserResp userResp = SecurityUtils.getCurrentUser(req);
        userInfoReq.setUserId(userResp.getId());
        userService.insertOrUpdateUserInfo(userInfoReq);
        return "r:/user/personCenter";
    }

    @GetMapping("/blog")
    public String blog(HttpServletRequest req, @RequestParam("folderId") Long folderId) {
        if (!SecurityUtils.isLogin(req)) {
            ResultUtils.buildFail(req, ResultEnums.USER_NOT_LOGIN);
            return "r:/user/toLogin";
        }
        UserResp userResp = SecurityUtils.getCurrentUser(req);
        long userId = userResp.getId();
        long folderIdL = folderId == null ? 0 : folderId;
        Result folderResult = folderService.findFoldersByFolderId(folderIdL, userId);
        if (!folderResult.isSuccess()) {
            ResultUtils.buildFail(req, folderResult);
        } else {
            Result noteResult = noteService.findNotesByFolderId(folderIdL, userId);
            if (!noteResult.isSuccess()) {
                ResultUtils.buildFail(req, noteResult);
            } else {
                List<FolderResp> folderBreadcrumb = findFolderBreadcrumb(folderIdL);
                Map<String, Object> result = Maps.newHashMap();
                result.put("folders", folderResult.getData());
                result.put("notes", noteResult.getData());
                result.put("folderId", folderIdL);
                result.put("folderBreadcrumb", folderBreadcrumb);
                ResultUtils.buildSuccess(req, result);
            }
        }
        return "personCenter/person_blog";
    }

    private List<FolderResp> findFolderBreadcrumb(long folderId) {
        List<FolderResp> folderResps = Lists.newArrayList();
        while (folderId != 0) {
            Result result = folderService.getFolderById(folderId);
            if (result.isSuccess()) {
                FolderResp folderResp = (FolderResp) result.getData();
                if (folderResp != null) {
                    folderResps.add(folderResp);
                    folderResps = Lists.reverse(folderResps);
                    folderId = folderResp.getParentFolderId();
                }
            }
        }
        return folderResps;
    }

}
