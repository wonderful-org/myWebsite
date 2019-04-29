package com.aviator.mywebsite.entity.dto.req;

/**
 * @Description TODO
 * @ClassName NoteReq
 * @Author aviator_ls
 * @Date 2019/4/27 21:56
 */
public class NoteReq {

    private String title;

    private String content;

    private String contentHtml;

    /**
     * 是否公开，0：公开， 1：不公开
     */
    private int open;

    private long folderId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }
}
