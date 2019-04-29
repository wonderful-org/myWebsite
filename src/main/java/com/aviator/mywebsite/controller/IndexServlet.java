package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;

import javax.servlet.annotation.WebServlet;

/**
 * @Description TODO
 * @ClassName IndexServlet
 * @Author aviator_ls
 * @Date 2019/4/26 19:40
 */
@WebServlet("")
public class IndexServlet extends BaseServlet {

    @GetMapping("")
    public String index() {
        return "index";
    }
}
