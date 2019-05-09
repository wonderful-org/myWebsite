package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.po.Note;

import java.util.List;

/**
 * @Description TODO
 * @ClassName NoteDao
 * @Author aviator_ls
 * @Date 2019/4/29 22:00
 */
public class NoteDao extends BaseDao {

    public List<Note> findNoteByFolderId(long folderId) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(NOTE_TABLE_NAME).append(" where ").append(" folderId = ?");
        return JdbcUtils.executeQueryForList(sql.toString(), Note.class, folderId);
    }

    @Override
    protected String getTableName() {
        return NOTE_TABLE_NAME;
    }
}
