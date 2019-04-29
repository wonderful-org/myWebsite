package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;
import com.aviator.mywebsite.entity.dto.req.PageReq;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

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
    public String notes(HttpServletRequest req, PageReq pageReq) {

        return "notes";
    }
}
