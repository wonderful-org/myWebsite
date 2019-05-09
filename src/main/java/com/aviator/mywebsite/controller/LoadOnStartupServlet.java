package com.aviator.mywebsite.controller;

import com.aviator.mywebsite.entity.dto.req.UserReq;
import com.aviator.mywebsite.exception.ControllerException;
import com.aviator.mywebsite.service.UserService;
import com.aviator.mywebsite.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description TODO
 * @ClassName LoadOnStartupServlet
 * @Author aviator_ls
 * @Date 2019/5/8 20:29
 */
public class LoadOnStartupServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoadOnStartupServlet.class);

    private static final String INIT_PROPERTIES_NAME = "init.properties";

    private static final String INIT_PROPERTIES_SPLIT = ",";

    private static Properties properties;

    @Override
    public void init() throws ServletException {
        loadInitProperties();
        initAdminAccount();
    }

    private void loadInitProperties() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(INIT_PROPERTIES_NAME);
        if (in == null) {
            log.error("can not find " + INIT_PROPERTIES_NAME);
            throw new ControllerException("can not find " + INIT_PROPERTIES_NAME);
        }
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            log.error("load properties error: ", e);
            throw new ControllerException("load properties error: ", e);
        }
        properties = prop;
    }

    private void initAdminAccount() {
        String username = properties.getProperty("user.admin.username");
        String password = properties.getProperty("user.admin.password");
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            log.warn("initAdminAccount username or password blank, username:{}, password:{}", username, password);
            return;
        }
        UserService userService = new UserService();
        UserReq userReq = new UserReq();
        userReq.setUsername(username);
        userReq.setPassword(password);
        userService.register(userReq);
        SecurityUtils.USER_ADMIN_USERNAME = username;
        log.info("initAdminAccount success");
    }
}
