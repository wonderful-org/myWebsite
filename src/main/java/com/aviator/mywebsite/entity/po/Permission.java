package com.aviator.mywebsite.entity.po;


import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description 权限
 * @ClassName Permission
 * @Author aviator_ls
 * @Date 2019/4/25 14:56
 */
public class Permission extends BaseEntity {

    private long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限资源
     */
    private String resource;

    /**
     * 权限类型，0：读写 1：只读
     */
    private int type;

    /**
     * 权限描述
     */
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
