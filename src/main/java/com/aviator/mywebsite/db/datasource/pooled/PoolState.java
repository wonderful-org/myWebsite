package com.aviator.mywebsite.db.datasource.pooled;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @ClassName PoolState
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/18 11:40
 */
public class PoolState {

    protected PooledDataSource dataSource;

    protected List<PooledConnection> activeConnections = Lists.newArrayList();

    protected List<PooledConnection> idleConnections = Lists.newArrayList();

    protected int badConnectionCount;

    protected int claimedOverdueConnectionCount;

    public PoolState(PooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
