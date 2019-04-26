package com.aviator.mywebsite.util;

/**
 * @Description TODO
 * @ClassName SecurityUtilsTest
 * @Author aviator_ls
 * @Date 2019/4/25 22:05
 */
public class SecurityUtilsTest {

    public static void main(String[] args) {
        String pw ="123456";
        String salt = SecurityUtils.produceSalt();
        String ciphertext1 = SecurityUtils.encrypt(pw, salt);
        System.out.println("ciphertext1: " + ciphertext1 + "----- length: " + ciphertext1.length());
        String ciphertext2 = SecurityUtils.encrypt(pw, salt);
        System.out.println("ciphertext2: " + ciphertext2 + "----- length: " + ciphertext2.length());
        System.out.println("ciphertext1 equals ciphertext2: " + ciphertext1.equals(ciphertext2));
    }
}
