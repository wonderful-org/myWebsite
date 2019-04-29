package com.aviator.mywebsite.dao;

/**
 * @Description TODO
 * @ClassName NoteDao
 * @Author aviator_ls
 * @Date 2019/4/29 22:00
 */
public class NoteDao extends BaseDao {

    @Override
    protected String getTableName() {
        return NOTE_TABLE_NAME;
    }
}
