package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.po.UserInfo;
import com.google.common.collect.Maps;

import java.util.Map;

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
        return insert(userInfo);
    }

    public int deleteUserByUserId(long userId) {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(USER_INFO_TABLE_NAME).append(" where ").append(" userId = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), userId);
    }

    public int updateUserByUserId(long userId, UserInfo userInfo) {
        Map<String, Object> condMap = Maps.newHashMap();
        condMap.put("userId", userId);
        return update(userInfo, condMap);
    }

    @Override
    protected String getTableName() {
        return USER_INFO_TABLE_NAME;
    }
}
