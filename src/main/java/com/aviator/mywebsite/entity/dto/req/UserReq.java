package com.aviator.mywebsite.entity.dto.req;

import com.aviator.mywebsite.entity.BaseEntity;

public class UserReq extends BaseEntity {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
