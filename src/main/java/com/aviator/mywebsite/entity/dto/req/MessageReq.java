package com.aviator.mywebsite.entity.dto.req;

import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description TODO
 * @ClassName MessageReq
 * @Author aviator_ls
 * @Date 2019/4/28 18:30
 */
public class MessageReq extends BaseEntity {

    private Long id;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
