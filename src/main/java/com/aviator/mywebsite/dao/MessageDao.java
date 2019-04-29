package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.entity.cond.MessageCond;
import com.aviator.mywebsite.entity.po.Message;
import com.aviator.mywebsite.entity.po.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
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

    public Page<Message> findMessagePage(MessageCond cond) {
        long count = getCount();
        if (count < 1) {
            return new Page<>();
        }
        int pageNum = cond.getPageNum();
        int pageSize = cond.getPageSize();
        if (pageNum < 1) {
            pageNum = Page.DEFAULT_PAGE_NUM;
        }
        if (pageSize < 1) {
            pageSize = Page.DEFAULT_PAGE_SIZE;
        }
        int firstResult = (pageNum - 1) * pageSize;
        if (firstResult >= count) {
            return new Page<>();
        }
        String orderBy = cond.getOrderBy();
        boolean isAsc = cond.isAsc();
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(MESSAGE_TABLE_NAME);
        if (StringUtils.isNotBlank(orderBy)) {
            sql.append(" order by ").append(orderBy).append(isAsc ? " asc " : " desc ");
        }
        sql.append(" limit ").append(firstResult).append(",").append(pageSize);
        List<Message> result = JdbcUtils.executeQueryForList(sql.toString(), Message.class);
        if (StringUtils.isBlank(orderBy)) {
            return new Page(pageNum, pageSize, result, count);
        }
        return new Page(pageNum, pageSize, result, count, orderBy, isAsc);
    }

    @Override
    protected String getTableName() {
        return MESSAGE_TABLE_NAME;
    }
}
