package com.aviator.mywebsite.entity.dto.req;

import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description TODO
 * @ClassName FolderReq
 * @Author aviator_ls
 * @Date 2019/4/27 21:54
 */
public class FolderReq extends BaseEntity {

    private String folderName;

    private String description;

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
}
