package com.aviator.mywebsite.entity.cond;

/**
 * @Description TODO
 * @ClassName FolderCond
 * @Author aviator_ls
 * @Date 2019/5/9 19:59
 */
public class FolderCond extends BaseCond {

    private Long authorId;

    private long parentFolderId;

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
