package com.aviator.mywebsite.controller;

import com.alibaba.fastjson.JSON;
import com.aviator.mywebsite.annotation.*;
import com.aviator.mywebsite.exception.ControllerException;
import com.aviator.mywebsite.service.MessageService;
import com.aviator.mywebsite.service.UserService;
import com.aviator.mywebsite.util.ServletUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * description: servlet基类，重写了doGet/doPost方法，配合GetMapping/PostMapping等注解可以使servlet根据不同请求uri分发到不同方法来处理
 * 子类不应再重写doGet/doPost/service方法，应根据不同请求uri编写不同方法，使用注解可以制定方法处理的对应uri
 * 方法返回值为跳转视图路径，前缀redirect:或r:表示重定向，forward:或f:表示转发
 * create time: 2019/4/26 12:53
 * create by: aviator_ls
 */
public abstract class BaseServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(BaseServlet.class);

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";

    protected static final String VIEW_OPERATE_SEPARATOR = ":";

    protected static final String VIEW_SUFFIX = ".jsp";

    protected static final String VIEW_PATH = "/WEB-INF/jsp/";

    protected UserService userService = new UserService();

    protected MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        uri = uri.contains(req.getContextPath()) ? StringUtils.substringAfter(uri, req.getContextPath()) : uri;
        String mappingUri = getMappingUri(uri);
        // 通过注解找到匹配的方法
        Method method = findMethod(mappingUri, req);
        if (method == null) {
            log.error("can not find mapping uri: {}", uri);
            req.getRequestDispatcher(VIEW_PATH + "404" + VIEW_SUFFIX).forward(req, resp);
            return;
        }
        Object resultObj;
        try {
            // 方法参数列表类型数组
            Class[] parameterTypes = method.getParameterTypes();
            // 方法参数列表注解
            Annotation[][] annotationss = method.getParameterAnnotations();
            // 最后method.invoke传入的实参数组
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class parameterType = parameterTypes[i];
                Object ag;
                if (parameterType == HttpServletRequest.class) {
                    ag = req;
                } else if (parameterType == HttpServletResponse.class) {
                    ag = resp;
                } else {
                    Annotation[] annotations = annotationss[i];
                    // 该参数包含RequestBody注解，则把传来的数据当作json，从request流中拿json反序列化为参数
                    if (containsRequestBody(annotations)) {
                        ag = ServletUtils.getByStream(req, parameterType);
                    } else {
                        // 该参数包含RequestParam注解且注解value有值，则根据value从request的parameterMap中取值
                        // 不包含RequestParam注解或注解value没有值，则直接将parameterMap反序列化为参数
                        RequestParam rp = getRequestParam(annotations);
                        if (parameterType == String.class && rp != null && StringUtils.isNotBlank(rp.value())) {
                            Map<String, Object> paramMap = ServletUtils.getParams(req);
                            ag = paramMap.get(rp.value());
                        } else {
                            ag = ServletUtils.getParams(req, parameterType);
                        }
                    }
                }
                args[i] = ag;
            }
            if (ArrayUtils.isEmpty(parameterTypes)) {
                resultObj = method.invoke(this);
            } else {
                resultObj = method.invoke(this, args);
            }
        } catch (Exception e) {
            log.error("servlet method invoke error ", e);
            throw new ControllerException("servlet method invoke error", e);
        }
        if (resultObj == null) {
            return;
        }
        // 如果方法有responseBody注解，判断为ajax请求，返回json
        if (!(resultObj instanceof String) || method.isAnnotationPresent(ResponseBody.class)) {
            resp.setContentType("text/json;charset=UTF-8");
            try (PrintWriter writer = resp.getWriter()) {
                writer.print(JSON.toJSONString(resultObj));
                writer.flush();
            }
            return;
        }
        String result = (String) resultObj;
        if (StringUtils.isBlank(result)) {
            return;
        }
        // 解析返回值
        if (result.indexOf(VIEW_OPERATE_SEPARATOR) > 0) {
            String operate = StringUtils.substringBefore(result, VIEW_OPERATE_SEPARATOR);
            String lowerOperate = operate.toLowerCase();
            String path = StringUtils.substringAfter(result, VIEW_OPERATE_SEPARATOR);
            if (lowerOperate.charAt(0) == 'r') {
                path = path.indexOf("/") == 0 ? req.getContextPath() + path : path;
                resp.sendRedirect(path);
            } else if (lowerOperate.charAt(0) == 'f') {
                req.getRequestDispatcher(path).forward(req, resp);
            } else {
                log.error("servlet skip error, operate:{}", operate);
                throw new ControllerException("servlet skip error");
            }
        } else {
            req.getRequestDispatcher(VIEW_PATH + result + VIEW_SUFFIX).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private String getMappingUri(String uri) {
        uri = removeFirstStr(uri, "/");
        String mappingUri = uri.indexOf("/") < 0 ? "" : StringUtils.substringAfter(uri, "/").replace(" ", "");
        mappingUri = removeFirstStr(mappingUri, "/");
        return mappingUri;
    }

    private Method findMethod(String mappingUri, HttpServletRequest req) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String reqMethod = req.getMethod();
            String atValue = "";
            switch (reqMethod) {
                case METHOD_GET:
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        atValue = method.getAnnotation(GetMapping.class).value();
                    }
                    break;
                case METHOD_POST:
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        atValue = method.getAnnotation(PostMapping.class).value();
                    }
                    break;
                case METHOD_PUT:
                    if (method.isAnnotationPresent(PutMapping.class)) {
                        atValue = method.getAnnotation(PutMapping.class).value();
                    }
                    break;
                case METHOD_DELETE:
                    if (method.isAnnotationPresent(DeleteMapping.class)) {
                        atValue = method.getAnnotation(DeleteMapping.class).value();
                    }
                    break;
            }
            String getMappingValue = StringUtils.isBlank(atValue) ? "" : atValue.replace(" ", "");
            String getMappingStr = getMappingValue.indexOf("/") < 0 ? getMappingValue : StringUtils.substringAfter(getMappingValue, "/");
            if (StringUtils.equals(mappingUri, getMappingStr)) {
                return method;
            }
        }
        return null;
    }

    private String removeFirstStr(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        while (str.indexOf(separator) == 0) {
            str = StringUtils.substringAfter(str, separator);
        }
        return str;
    }

    private boolean containsRequestBody(Annotation[] annotations) {
        if (ArrayUtils.isNotEmpty(annotations)) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestBody) {
                    return true;
                }
            }
        }
        return false;
    }

    private RequestParam getRequestParam(Annotation[] annotations) {
        if (ArrayUtils.isNotEmpty(annotations)) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestParam) {
                    return (RequestParam) annotation;
                }
            }
        }
        return null;
    }

}
