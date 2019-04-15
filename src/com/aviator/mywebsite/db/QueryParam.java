package com.aviator.mywebsite.db;

import java.util.List;

/**
 * @ClassName QueryParam
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/15 18:38
 */
public class QueryParam {

    private String sql;

    private List params;

    public QueryParam(String sql, List params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List getParams() {
        return params;
    }

    public void setParams(List params) {
        this.params = params;
    }
}
