package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.po.Note;

/**
 * @Description TODO
 * @ClassName NoteDao
 * @Author aviator_ls
 * @Date 2019/4/29 22:00
 */
public class NoteDao extends BaseDao {

    public Note getNoteById(long id) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(NOTE_TABLE_NAME).append(" where ").append(" id = ? ");
        return JdbcUtils.executeQueryForObject(sql.toString(), Note.class, id);
    }

    public int deleteNoteById(long id) {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(NOTE_TABLE_NAME).append(" where ").append(" id = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), id);
    }

    @Override
    protected String getTableName() {
        return NOTE_TABLE_NAME;
    }
}
