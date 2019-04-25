package com.aviator.mywebsite.entity.po;

import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description 角色
 * @ClassName Role
 * @Author aviator_ls
 * @Date 2019/4/25 12:54
 */
public class Role extends BaseEntity {

    private long id;

    private String name;

    private String desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
