package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.annotation.GetMapping;

import javax.servlet.annotation.WebServlet;

/**
 * @Description TODO
 * @ClassName AboutServlet
 * @Author aviator_ls
 * @Date 2019/5/4 19:32
 */
@WebServlet("/about")
public class AboutServlet extends BaseServlet {

    @GetMapping("")
    public String toAbout() {
        return "about";
    }
}
