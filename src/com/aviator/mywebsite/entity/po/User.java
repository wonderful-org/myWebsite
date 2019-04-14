package com.aviator.mywebsite.entity.po;

import com.aviator.mywebsite.entity.BaseEntity;

public class User extends BaseEntity {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
