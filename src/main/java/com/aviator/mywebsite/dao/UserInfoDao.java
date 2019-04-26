package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.po.UserInfo;

/**
 * @Description TODO
 * @ClassName UserInfoDao
 * @Author aviator_ls
 * @Date 2019/4/25 20:27
 */
public class UserInfoDao extends BaseDao {

    public UserInfo getUserInfoByUserId(long userId) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(USER_INFO_TABLE_NAME).append(" where ").append(" userId = ?");
        return JdbcUtils.executeQueryForObject(sql.toString(), UserInfo.class, userId);
    }

    public long insertUserInfo(UserInfo userInfo) {
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(USER_INFO_TABLE_NAME).append(" (userId, nickname, email, gender, profile, introduction, createTime, updateTime) ").append(" values ").append(" (?,?,?,?,?,?,?,?) ");
        return JdbcUtils.executeInsert(sql.toString(), long.class, userInfo.getUserId(), userInfo.getNickname(), userInfo.getEmail(), userInfo.getGender(), userInfo.getProfile(), userInfo.getIntroduction(), userInfo.getCreateTime(), userInfo.getUpdateTime());
    }

    public int deleteUserByUserId(long userId) {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(USER_INFO_TABLE_NAME).append(" where ").append(" userId = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), userId);
    }

    public int updateUserByUserId(long userId, UserInfo userInfo) {
        StringBuilder sql = new StringBuilder("update ");
        sql.append(USER_INFO_TABLE_NAME).append(" set ").append(" nickname = ?, email = ?, gender = ?, profile = ?, introduction = ?, createTime = ?, updateTime = ? where userId = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), userInfo.getNickname(), userInfo.getEmail(), userInfo.getGender(), userInfo.getProfile(), userInfo.getIntroduction(), userInfo.getCreateTime(), userInfo.getUpdateTime(), userId);
    }
}
