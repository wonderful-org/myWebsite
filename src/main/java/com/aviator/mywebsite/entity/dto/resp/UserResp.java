package com.aviator.mywebsite.entity.dto.resp;

import com.aviator.mywebsite.entity.BaseEntity;

public class UserResp extends BaseEntity {

    private long id;

    private String username;

    private UserInfoResp userInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserInfoResp getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoResp userInfo) {
        this.userInfo = userInfo;
    }
}
