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
    private long id;

    private long userId;

    private String username;

    private String realName;

    private String nickname;

    private int gender;

    private String birthday;

    private String phone;

    private String personWebsite;

    private String email;

    private String address;

    private String introduction;

    /**
     * 头像
     */
    private String profile;

    private Date createTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonWebsite() {
        return personWebsite;
    }

    public void setPersonWebsite(String personWebsite) {
        this.personWebsite = personWebsite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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
