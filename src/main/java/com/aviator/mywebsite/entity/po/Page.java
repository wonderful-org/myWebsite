package com.aviator.mywebsite.entity.po;

import com.aviator.mywebsite.entity.BaseEntity;

import java.util.List;

/**
 * Created by aviator_ls on 2018/7/16.
 */
public class Page<T> extends BaseEntity {
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int DEFAULT_PAGE_NUM = 1;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private int pageNum = DEFAULT_PAGE_NUM;

    private List<T> data;

    private long pageCount;

    private long totalCount;

    private String orderBy;

    private boolean isAsc;

    public Page() {
        this(0, 0, 0);
    }

    public Page(int pageNum, int pageSize, long totalCount) {
        this(pageNum, pageSize, null, totalCount);
    }

    public Page(int pageNum, int pageSize, List<T> data, long totalCount) {
        this(pageNum, pageSize, data, totalCount, null, true);
    }

    public Page(int pageNum, int pageSize, List<T> data, long totalCount, String orderBy, boolean isAsc) {
        pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
        pageNum = pageNum < 1 ? 1 : pageNum;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.data = data;
        this.totalCount = totalCount;
        this.orderBy = orderBy;
        this.isAsc = isAsc;
        getPageCount();
    }

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getPageCount() {
        this.pageCount = this.totalCount % this.pageSize == 0 ? (this.totalCount / this.pageSize) : (this.totalCount / this.pageSize + 1);
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getAsc() {
        return isAsc;
    }

    public void setAsc(Boolean asc) {
        isAsc = asc;
    }

}
