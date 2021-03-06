package com.aviator.mywebsite.service;

import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.UserInfoReq;
import com.aviator.mywebsite.entity.dto.req.UserReq;
import com.aviator.mywebsite.entity.dto.resp.UserInfoResp;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.entity.po.User;
import com.aviator.mywebsite.entity.po.UserInfo;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @Description TODO
 * @ClassName UserService
 * @Author aviator_ls
 * @Date 2019/4/25 20:26
 */
public class UserService extends BaseService {

    public Result register(UserReq userReq) {
        Result result = checkUser(userReq);
        if (result != null) {
            return result;
        }
        result = checkUser(userReq);
        if (result != null) {
            return result;
        }
        User user = convertFromDTO(userReq, User.class);
        User dbUser = userDao.getUserByUsername(userReq.getUsername());
        if (dbUser != null) {
            return ResultUtils.buildResult(ResultEnums.USER_EXIST);
        }
        String salt = SecurityUtils.produceSalt();
        String ciphertext = SecurityUtils.encrypt(userReq.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(ciphertext);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        long userId = userDao.insertUser(user);
        UserInfo userInfo = user.getUserInfo();
        if (userInfo != null) {
            UserInfo dbUserInfo = userInfoDao.getUserInfoByUserId(userId);
            if (dbUserInfo != null) {
                userInfoDao.deleteUserByUserId(userId);
            }
            userInfo.setUserId(userId);
            userInfo.setUsername(user.getUsername());
            userInfo.setNickname(userInfo.getNickname());
            userInfoDao.insert(userInfo);
        }
        UserResp userResp = new UserResp();
        userResp.setId(userId);
        userResp.setUsername(user.getUsername());
        return ResultUtils.buildResult(ResultEnums.SUCCESS, userResp);
    }

    public Result login(UserReq userReq) {
        Result result = checkUser(userReq);
        if (result != null) {
            return result;
        }
        User user = convertFromDTO(userReq, User.class);
        User dbUser = userDao.getUserByUsername(user.getUsername());
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_USERNAME_PASSWORD_ERROR);
        }
        String salt = dbUser.getSalt();
        String userCiphertext = SecurityUtils.encrypt(user.getPassword(), salt);
        String dbUserCiphertext = dbUser.getPassword();
        if (!StringUtils.equals(userCiphertext, dbUserCiphertext)) {
            return ResultUtils.buildResult(ResultEnums.USER_USERNAME_PASSWORD_ERROR);
        }
        dbUser.setPassword(null);
        UserResp userResp = convertToDTO(dbUser, UserResp.class);
        UserInfo dbUserInfo = userInfoDao.getUserInfoByUserId(userResp.getId());
        UserInfoResp userInfoResp = convertToDTO(dbUserInfo, UserInfoResp.class);
        userResp.setUserInfo(userInfoResp);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, userResp);
    }

    public Result insertOrUpdateUserInfo(UserInfoReq userInfoReq) {
        Long userId = userInfoReq.getUserId();
        if (userId == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_LOGIN);
        }
        User dbUser = userDao.getUserById(userId);
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_EXIST);
        }
        UserInfo dbUserInfo = userInfoDao.getUserInfoByUserId(userId);
        UserInfo userInfo = convertFromDTO(userInfoReq, UserInfo.class);
        long id;
        Date currentDate = new Date();
        if (dbUserInfo == null) {
            userInfo.setCreateTime(currentDate);
            userInfo.setUpdateTime(currentDate);
            id = userInfoDao.insert(userInfo);
        } else {
            userInfo.setCreateTime(dbUserInfo.getCreateTime());
            userInfo.setUpdateTime(currentDate);
            id = userInfoDao.updateUserInfoByUserId(userId, userInfo);
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, id);
    }

    public Result getUserInfoByUserId(long userId) {
        User dbUser = userDao.getUserById(userId);
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_EXIST);
        }
        UserInfo dbUserInfo = userInfoDao.getUserInfoByUserId(userId);
        UserInfoResp userInfoResp = convertToDTO(dbUserInfo, UserInfoResp.class);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, userInfoResp);
    }

    private Result checkUser(UserReq userReq) {
        Result result = checkParams(userReq);
        if (result != null) {
            return result;
        }
        String username = userReq.getUsername();
        String password = userReq.getPassword();
        if (StringUtils.isBlank(username) || !username.matches("[a-zA-Z0-9_-]{5,20}")) {
            return ResultUtils.buildResult(ResultEnums.USER_USERNAME_FORMAT_ERROR);
        }
        if (StringUtils.isBlank(password) || !password.matches("^[a-zA-Z0-9!@#$%^&*()-_=+/.,;':\"~`{}\\[\\]\\\\]{6,30}$")) {
            return ResultUtils.buildResult(ResultEnums.USER_PASSWORD_FORMAT_ERROR);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("1334--4".matches("[a-zA-Z0-9_-]{5,20}"));
        System.out.println("1dfdsfs+-*/dfsdf".matches("^^[a-zA-Z0-9!@#$%^&*()-_=+/.,;':\"~`{}\\[\\]\\\\]{6,30}$"));
    }
}
