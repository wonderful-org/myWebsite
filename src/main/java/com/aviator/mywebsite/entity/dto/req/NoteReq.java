package com.aviator.mywebsite.entity.dto.req;

import java.util.List;

/**
 * @Description TODO
 * @ClassName NoteReq
 * @Author aviator_ls
 * @Date 2019/4/27 21:56
 */
public class NoteReq {

    private long id;

    private Long authorId;

    private String title;

    private String source;

    private String content;

    /**
     * 是否公开，0：公开， 1：不公开
     */
    private int open;

    private long folderId;

    private List<String> imgUrls;

    private String folderName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }


    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}
