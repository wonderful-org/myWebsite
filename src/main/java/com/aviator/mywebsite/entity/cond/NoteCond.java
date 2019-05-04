package com.aviator.mywebsite.entity.cond;

import java.util.List;

/**
 * @Description TODO
 * @ClassName NoteCond
 * @Author aviator_ls
 * @Date 2019/5/3 17:56
 */
public class NoteCond extends BaseCond {
    private int open;

    private List createTime;

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public List getCreateTime() {
        return createTime;
    }

    public void setCreateTime(List createTime) {
        this.createTime = createTime;
    }
}
