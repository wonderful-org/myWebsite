package com.aviator.mywebsite.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName TestFilter
 * @Description 测试filter
 * @Author aviator_ls
 * @Date 2019/4/14 12:16
 */
public class TestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 向下转型request，以便使用子类方法
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 获取请求url
        String url = request.getRequestURL().toString();
        System.out.println("请求url为：" + url);
        // 获取请求客户端ip
        String ip = request.getRemoteAddr();
        System.out.println("请求ip为：" + ip);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
