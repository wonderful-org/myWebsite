package com.aviator.mywebsite.entity.dto.resp;

import java.util.Date;

/**
 * @Description TODO
 * @ClassName NoteResp
 * @Author aviator_ls
 * @Date 2019/5/3 17:56
 */
public class NoteResp {

    private long id;

    private UserResp author;

    private UserInfoResp authorInfo;

    private long folderId;

    private String title;

    private String source;

    private String content;

    private String contentText;

    /**
     * 是否公开，0：公开，1：不公开
     */
    private int open;

    private Date createTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
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
