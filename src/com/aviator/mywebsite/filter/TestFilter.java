package com.aviator.mywebsite.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 向下转型request，以便使用子类方法
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 获取请求url
        String url = request.getRequestURL().toString();
        log.info("请求url为：" + url);
        // 获取请求客户端ip
        String ip = request.getRemoteAddr();
        log.info("请求ip为：" + ip);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
