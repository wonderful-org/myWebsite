package com.aviator.mywebsite.db.datasource.pooled;


import com.aviator.mywebsite.db.datasource.unpooled.UnPooledDataSourceFactory;

/**
 * @ClassName PooledDataSourceFactory
 * @Description TODO
 * @Author aviator_ls
 * @Date 2019/4/18 10:45
 */
public class PooledDataSourceFactory extends UnPooledDataSourceFactory {

    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }
}
