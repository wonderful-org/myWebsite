package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.entity.po.User;

import java.util.Date;

/**
 * @Description TODO
 * @ClassName UserDaoTest
 * @Author aviator_ls
 * @Date 2019/4/25 17:24
 */
public class UserDaoTest {

    private static UserDao userDao = new UserDao();

    private static User user;

    public static void main(String[] args) {
        insertUser();
        getUserByUsername();
        updateUserByUsername();
        findUsers();
        deleteUserByUsername();
        findUsers();
    }

    static {
        user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setSalt("testSalt");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
    }

    public static void insertUser() {
        userDao.insertUser(user);
    }

    public static void getUserByUsername() {
        System.out.println(userDao.getUserByUsername(user.getUsername()));
    }

    public static void updateUserByUsername() {
        userDao.updateUserByUsername(user);
    }

    public static void findUsers() {
        System.out.println(userDao.findUsers());
    }

    public static void deleteUserByUsername() {
        userDao.deleteUserByUsername(user.getUsername());
    }

}
