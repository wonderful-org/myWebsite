package com.aviator.mywebsite.entity.po;

import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description 用户信息
 * @ClassName UserInfo
 * @Author aviator_ls
 * @Date 2019/4/25 15:04
 */
public class UserInfo extends BaseEntity {

    private long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 性别，0：未知 1：男 2：女
     */
    private int gender;

    /**
     * 头像
     */
    private String profile;

    /**
     * 用户简介
     */
    private String introduction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
