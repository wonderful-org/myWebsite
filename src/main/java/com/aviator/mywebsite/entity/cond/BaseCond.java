package com.aviator.mywebsite.entity.cond;

import com.aviator.mywebsite.entity.BaseEntity;

/**
 * @Description TODO
 * @ClassName BaseCond
 * @Author aviator_ls
 * @Date 2019/4/28 18:39
 */
public abstract class BaseCond extends BaseEntity {

    private int pageNum;

    private int pageSize;

    private String orderBy;

    private boolean isAsc;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
