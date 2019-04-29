package com.aviator.mywebsite.entity.dto.req;

import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description TODO
 * @ClassName PageReq
 * @Author aviator_ls
 * @Date 2019/4/29 14:07
 */
public class PageReq extends BaseEntity {

    private int pageSize;

    private int pageNum;

    private String orderBy;

    private boolean isAsc;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }
}
