package com.aviator.mywebsite.entity.dto.resp;

import com.aviator.mywebsite.entity.BaseEntity;

import java.util.Date;

/**
 * @Description TODO
 * @ClassName MessageResp
 * @Author aviator_ls
 * @Date 2019/4/29 13:43
 */
public class MessageResp extends BaseEntity {

    private long id;

    private String content;

    private UserResp author;

    private UserInfoResp authorInfo;

    private Date createTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserResp getAuthor() {
        return author;
    }

    public void setAuthor(UserResp author) {
        this.author = author;
    }

    public UserInfoResp getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(UserInfoResp authorInfo) {
        this.authorInfo = authorInfo;
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
