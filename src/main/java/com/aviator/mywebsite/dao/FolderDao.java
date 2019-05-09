package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.po.Folder;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Description TODO
 * @ClassName FolderDao
 * @Author aviator_ls
 * @Date 2019/5/8 21:12
 */
public class FolderDao extends BaseDao {

    public List<Folder> findFoldersByUserId(long userId) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(FOLDER_TABLE_NAME).append(" where authorId = ?");
        List list = Lists.newArrayList();
        list.add(userId);
        return JdbcUtils.executeQueryForList(sql.toString(), list, Folder.class);
    }

    @Override
    protected String getTableName() {
        return FOLDER_TABLE_NAME;
    }
}
