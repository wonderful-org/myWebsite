package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.po.Message;

import java.util.Map;

/**
 * @Description TODO
 * @ClassName MessageDao
 * @Author aviator_ls
 * @Date 2019/4/28 18:30
 */
public class MessageDao extends BaseDao {

    public long insertMessage(Message message) {
        return insert(message);
    }

    public int updateMessageById(Message message, Map<String, Object> condMap) {
        return update(message, condMap);
    }

    public int deleteMessageById(long id) {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(MESSAGE_TABLE_NAME).append(" where ").append(" id = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), id);
    }

    public Message getMessageById(long id) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(MESSAGE_TABLE_NAME).append(" where ").append(" id = ? ");
        return JdbcUtils.executeQueryForObject(sql.toString(), Message.class, id);
    }

    @Override
    protected String getTableName() {
        return MESSAGE_TABLE_NAME;
    }
}
