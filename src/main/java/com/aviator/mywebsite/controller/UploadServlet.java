package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.PostMapping;
import com.aviator.mywebsite.entity.Result;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @ClassName UploadServlet
 * @Author aviator_ls
 * @Date 2019/4/30 9:36
 */
@WebServlet("/upload/*")
public class UploadServlet extends BaseServlet {

    @PostMapping("/note/img")
    public Result noteImg(HttpServletRequest request) {
        return uploadService.upload("noteImg", request);
    }
}
