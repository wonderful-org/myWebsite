package com.aviator.mywebsite.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Description TODO
 * @ClassName CharacterEncodingFilter
 * @Author aviator_ls
 * @Date 2019/4/26 19:56
 */
public class CharacterEncodingFilter extends OncePerRequestFilter {

    private String encoding;
    private boolean forceRequestEncoding;
    private boolean forceResponseEncoding;

    private static final String ENCODING = "encoding";
    private static final String FORCE_ENCODING = "forceEncoding";
    private static final String FORCE_REQUEST_ENCODING = "forceRequestEncoding";
    private static final String FORCE_RESPONSE_ENCODING = "forceResponseEncoding";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Enumeration<String> names = filterConfig.getInitParameterNames();
        if (names != null) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                String value = filterConfig.getInitParameter(name);
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                if (name.equalsIgnoreCase(ENCODING)) {
                    this.encoding = value;
                }
                if (name.equalsIgnoreCase(FORCE_ENCODING)) {
                    this.forceRequestEncoding = Boolean.parseBoolean(value);
                    this.forceResponseEncoding = Boolean.parseBoolean(value);
                }
                if (name.equalsIgnoreCase(FORCE_REQUEST_ENCODING)) {
                    this.forceRequestEncoding = Boolean.parseBoolean(value);
                }
                if (name.equalsIgnoreCase(FORCE_RESPONSE_ENCODING)) {
                    this.forceResponseEncoding = Boolean.parseBoolean(value);
                }
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.isNotBlank(encoding)) {
            if (forceRequestEncoding || StringUtils.isBlank(request.getCharacterEncoding())) {
                request.setCharacterEncoding(encoding);
            }
            if (forceResponseEncoding) {
                response.setCharacterEncoding(encoding);
            }
        }
        filterChain.doFilter(request, response);
    }
}
