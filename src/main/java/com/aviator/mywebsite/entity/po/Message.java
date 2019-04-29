package com.aviator.mywebsite.entity.po;

import com.aviator.mywebsite.entity.BaseEntity;

import java.util.Date;

/**
 * @Description 留言
 * @ClassName Message
 * @Author aviator_ls
 * @Date 2019/4/28 18:19
 */
public class Message extends BaseEntity {

    private long id;

    private long authorId;

    private String content;

    private Date createTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
