package com.aviator.mywebsite.service;

import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.dto.req.UserReq;
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
        Result result = checkParams(userReq, userReq.getUsername(), userReq.getPassword());
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
            userInfoDao.insertUserInfo(userInfo);
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS);
    }

    public Result login(UserReq userReq) {
        Result result = checkParams(userReq, userReq.getUsername(), userReq.getPassword());
        if (result != null) {
            return result;
        }
        User user = convertFromDTO(userReq, User.class);
        User dbUser = userDao.getUserByUsername(user.getUsername());
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USERNAME_PASSWORD_ERROR);
        }
        String salt = dbUser.getSalt();
        String userCiphertext = SecurityUtils.encrypt(user.getPassword(), salt);
        String dbUserCiphertext = dbUser.getPassword();
        if (!StringUtils.equals(userCiphertext, dbUserCiphertext)) {
            return ResultUtils.buildResult(ResultEnums.USERNAME_PASSWORD_ERROR);
        }
        dbUser.setPassword(null);
        UserResp userResp = convertToDTO(dbUser, UserResp.class);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, userResp);
    }
}
