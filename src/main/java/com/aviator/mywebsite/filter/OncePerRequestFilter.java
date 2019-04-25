package com.aviator.mywebsite.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 参考spring的OncePerRequestFilter实现，因为不使用servlet3新的异步特性，所以去除过滤器是否在异步分派中执行的判断
 * @ClassName OncePerRequestFilter
 * @Author aviator_ls
 * @Date 2019/4/25 11:17
 */
public abstract class OncePerRequestFilter implements Filter {

    public static final String ALREADY_FILTERED_SUFFIX = ".FILTERED";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            boolean alreadyFilteredAttribute = request.getAttribute(getAlreadyFilteredAttributeName()) != null;
            if (!alreadyFilteredAttribute && !shouldNotFilter(request)) {
                request.setAttribute(getAlreadyFilteredAttributeName(), Boolean.TRUE);
                try {
                    this.doFilterInternal(request, response, filterChain);
                } finally {
                    request.removeAttribute(getAlreadyFilteredAttributeName());
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            throw new FilterException("OncePerRequestFilter only support http requests");
        }
    }

    protected String getAlreadyFilteredAttributeName() {
        return this.getClass().getName() + ALREADY_FILTERED_SUFFIX;
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return false;
    }

    protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;
}
