package com.aviator.mywebsite.util;

import org.apache.commons.codec.digest.DigestUtils;

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

    private static String fixPlaintext(String plaintext, String salt) {
        return plaintext + salt;
    }
}
