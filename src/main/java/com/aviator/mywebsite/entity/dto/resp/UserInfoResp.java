package com.aviator.mywebsite.entity.dto.resp;

import com.aviator.mywebsite.entity.BaseEntity;

import java.util.Date;

/**
 * @Description TODO
 * @ClassName UserInfoResp
 * @Author aviator_ls
 * @Date 2019/4/29 13:44
 */
public class UserInfoResp extends BaseEntity {
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

    private Date createTime;

    private Date updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
