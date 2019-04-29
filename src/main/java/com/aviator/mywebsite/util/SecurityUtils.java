package com.aviator.mywebsite.util;

import com.aviator.mywebsite.entity.dto.resp.UserResp;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Description TODO
 * @ClassName SecurityUtils
 * @Author aviator_ls
 * @Date 2019/4/25 21:55
 */
public class SecurityUtils {

    public static final String USER_SESSION_ATTRIBUTE = "currentUser";

    public static String produceSalt() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String encrypt(String plaintext, String salt) {
        return DigestUtils.sha256Hex(fixPlaintext(plaintext, salt));
    }

    public static void setCurrentUser(HttpServletRequest request, UserResp userResp) {
        request.getSession().setAttribute(USER_SESSION_ATTRIBUTE, userResp);
    }

    public static UserResp getCurrentUser(HttpServletRequest request) {
        return (UserResp) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
    }

    public static boolean isLogin(HttpServletRequest request){
        return request.getSession().getAttribute(USER_SESSION_ATTRIBUTE) != null;
    }

    private static String fixPlaintext(String plaintext, String salt) {
        return plaintext + salt;
    }
}
