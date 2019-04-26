package com.aviator.mywebsite.dao;


import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.po.User;

import java.util.List;

/**
 * @Description TODO
 * @ClassName UserDao
 * @Author aviator_ls
 * @Date 2019/4/25 15:53
 */
public class UserDao extends BaseDao {

    public User getUserByUsername(String username) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(USER_TABLE_NAME).append(" where ").append(" username = ? ");
        return JdbcUtils.executeQueryForObject(sql.toString(), User.class, username);
    }

    public long insertUser(User user) {
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(USER_TABLE_NAME).append(" (username, password, salt, createTime, updateTime) ").append(" values ").append(" (?,?,?,?,?) ");
        return JdbcUtils.executeInsert(sql.toString(), long.class, user.getUsername(), user.getPassword(), user.getSalt(), user.getCreateTime(), user.getUpdateTime());
    }

    public int deleteUserByUsername(String username) {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(USER_TABLE_NAME).append(" where ").append(" username = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), username);
    }

    public int updateUserByUsername(User user) {
        StringBuilder sql = new StringBuilder("update ");
        sql.append(USER_TABLE_NAME).append(" set ").append(" password = ?, salt = ?, createTime = ?, updateTime = ? where username = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), user.getPassword(), user.getSalt(), user.getCreateTime(), user.getUpdateTime(), user.getUsername());
    }

    public List<User> findUsers() {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(USER_TABLE_NAME);
        return JdbcUtils.executeQueryForList(sql.toString(), User.class);
    }
}
