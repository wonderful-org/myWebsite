package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.annotation.PostMapping;
import com.aviator.mywebsite.annotation.RequestBody;
import com.aviator.mywebsite.annotation.RequestParam;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.NotePageReq;
import com.aviator.mywebsite.entity.dto.req.NoteReq;
import com.aviator.mywebsite.entity.dto.resp.NoteResp;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName NoteServlet
 * @Author aviator_ls
 * @Date 2019/4/29 21:53
 */
@WebServlet("/note/*")
public class NoteServlet extends BaseServlet {

    @GetMapping("/toNotes")
    public String toNotes() {
        return "f:notes";
    }

    @GetMapping("/notes")
    public String notes(HttpServletRequest request, NotePageReq pageReq) {
        Result pageResult = noteService.findNotePage(pageReq);
        Result archivesResult = noteService.getArchives();
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("page", pageResult.getData());
        resultMap.put("archives", archivesResult.getData());
        resultMap.put("dataType", pageReq == null || StringUtils.isBlank(pageReq.getBetweenDate()) ? 0 : 1);
        resultMap.put("betweenDate", pageReq == null || StringUtils.isBlank(pageReq.getBetweenDate()) ? "" : pageReq.getBetweenDate());
        if (pageResult.isSuccess()) {
            ResultUtils.buildSuccess(request, resultMap);
        } else {
            ResultUtils.buildFail(request, pageResult);
        }
        return "notes";
    }

    @GetMapping("/toAdd")
    public String toAdd(HttpServletRequest request) {
        if (!SecurityUtils.isLogin(request)) {
            ResultUtils.buildFail(request, ResultEnums.USER_NOT_LOGIN);
            return "r:/user/toLogin";
        }
        UserResp userResp = SecurityUtils.getCurrentUser(request);
        Result folderResult = folderService.findFoldersByUserId(userResp.getId());
        if (folderResult.isSuccess()) {
            ResultUtils.buildSuccess(request, folderResult);
        } else {
            ResultUtils.buildFail(request, folderResult);
        }
        return "add_note";
    }

    @PostMapping("add")
    public Result add(HttpServletRequest request, @RequestBody NoteReq noteReq) {
        if (SecurityUtils.isLogin(request)) {
            noteReq.setAuthorId(SecurityUtils.getCurrentUser(request).getId());
            return noteService.insertNote(request, noteReq);
        }
        return ResultUtils.buildResult(ResultEnums.USER_NOT_LOGIN);
    }

    @GetMapping("/detail")
    public String detail(HttpServletRequest request, @RequestParam("id") long id) {
        Result result = noteService.getNoteById(id);
        if (result.isSuccess()) {
            ResultUtils.buildSuccess(request, result);
        } else {
            ResultUtils.buildFail(request, result);
        }
        return "note";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, @RequestParam("id") long id) {
        Result result = noteService.deleteNote(request, id);
        if (result.isSuccess()) {
            ResultUtils.buildSuccess(request, result);
        } else {
            ResultUtils.buildFail(request, result);
            return "f:detail";
        }
        return "r:notes";
    }

    @GetMapping("/toUpdate")
    public String toUpdate(HttpServletRequest request, @RequestParam("id") long id) {
        if (!SecurityUtils.isLogin(request)) {
            ResultUtils.buildFail(request, ResultEnums.USER_NOT_LOGIN);
            return "f:detail";
        }
        Result noteResult = noteService.getNoteById(id);
        NoteResp noteResp = (NoteResp) noteResult.getData();
        UserResp userResp = SecurityUtils.getCurrentUser(request);
        if (userResp.getId() != noteResp.getAuthor().getId()) {
            ResultUtils.buildFail(request, ResultEnums.USER_HAS_NOT_PERMISSION);
            return "f:detail";
        }
        Result folderResult = folderService.findFoldersByUserId(userResp.getId());
        if (noteResult.isSuccess()) {
            Map<String, Object> result = Maps.newHashMap();
            result.put("note", noteResult.getData());
            result.put("folders", folderResult.getData());
            ResultUtils.buildSuccess(request, result);
        } else {
            ResultUtils.buildFail(request, noteResult);
            return "f:detail";
        }
        return "update_note";
    }

    @PostMapping("update")
    public Result update(HttpServletRequest request, @RequestBody NoteReq noteReq) {
        if (SecurityUtils.isLogin(request)) {
            noteReq.setAuthorId(SecurityUtils.getCurrentUser(request).getId());
            return noteService.updateNote(request, noteReq);
        }
        return ResultUtils.buildResult(ResultEnums.USER_NOT_LOGIN);
    }

    @PostMapping("unload")
    public Result unload(HttpServletRequest request, @RequestBody NoteReq noteReq) {
        if (SecurityUtils.isLogin(request)) {
            noteReq.setAuthorId(SecurityUtils.getCurrentUser(request).getId());
            return noteService.unload(request, noteReq);
        }
        return ResultUtils.buildResult(ResultEnums.USER_NOT_LOGIN);
    }

}