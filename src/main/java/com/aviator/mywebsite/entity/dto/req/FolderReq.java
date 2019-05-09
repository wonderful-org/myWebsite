package com.aviator.mywebsite.entity.dto.req;

import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description TODO
 * @ClassName FolderReq
 * @Author aviator_ls
 * @Date 2019/4/27 21:54
 */
public class FolderReq extends BaseEntity {

    private long id;

    private String folderName;

    private String description;

    private Long authorId;

    private long parentFolderId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }
}
