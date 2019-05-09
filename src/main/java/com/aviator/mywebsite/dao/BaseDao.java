package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.db.type.DefaultTypeRegistry;
import com.aviator.mywebsite.entity.cond.BaseCond;
import com.aviator.mywebsite.entity.cond.NoteCond;
import com.aviator.mywebsite.entity.po.Page;
import com.aviator.mywebsite.entity.po.UserInfo;
import com.aviator.mywebsite.exception.DaoException;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaseDao {

    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);

    protected static final String USER_TABLE_NAME = "mw_user";

    protected static final String USER_INFO_TABLE_NAME = "mw_user_info";

    protected static final String MESSAGE_TABLE_NAME = "mw_message";

    protected static final String NOTE_TABLE_NAME = "mw_note";

    protected static final String FOLDER_TABLE_NAME = "mw_folder";

    public <T> long insert(T obj) {
        return insert(obj, true);
    }

    public <T> long insert(T obj, boolean skipId) {
        Class objType = obj.getClass();
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(getTableName()).append(getObjectFieldNamesSql(objType, skipId)).append(" values ").append(getObjectFieldNamesPreparedSql(objType, skipId));
        return JdbcUtils.executeInsert(sql.toString(), objectToParamLists(obj, getObjectFieldNames(objType, skipId)), long.class);
    }

    public <T> int update(T obj) {
        return update(obj, null);
    }

    public <T> int update(T obj, Map<String, Object> condMap) {
        Class objType = obj.getClass();
        StringBuilder sb = new StringBuilder("update ");
        sb.append(getTableName()).append(" set ").append(getUpdateObjectFieldNamesSql(obj.getClass()));
        List<List> paramLists = objectToParamLists(obj, getObjectFieldNames(objType));
        String sql = sb.toString();
        if (MapUtils.isNotEmpty(condMap)) {
            sb.append(" where ");
            List<List> condLists = Lists.newArrayList();
            for (Map.Entry<String, Object> entry : condMap.entrySet()) {
                List condList = Lists.newArrayList();
                sb.append(entry.getKey()).append(" = ?,");
                condList.add(entry.getValue());
                condLists.add(condList);
            }
            sql = sb.subSequence(0, sb.lastIndexOf(",")).toString();
            paramLists.addAll(condLists);
        }
        return JdbcUtils.executeUpdate(sql, paramLists);
    }

    public <T> T getById(long id, Class<T> requiredType) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(getTableName()).append(" where ").append(" id = ? ");
        return JdbcUtils.executeQueryForObject(sql.toString(), requiredType, id);
    }

    public int deleteById(long id) {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(getTableName()).append(" where ").append(" id = ? ");
        return JdbcUtils.executeUpdate(sql.toString(), id);
    }

    public int deleteByIds(List<Long> ids) {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(getTableName()).append(" where ").append(" id in (?");
        List<Object> params = Lists.newArrayList();
        for (Long id : ids) {
            sql.append(",?");
            params.add(id);
        }
        String sqlStr = StringUtils.substringBeforeLast(sql.toString(), ",?") + ")";
        return JdbcUtils.executeUpdate(sqlStr, params);
    }

    public <T> Page findPage(BaseCond cond, Class<T> resultType) {
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
        sql.append(getTableName());
        List params = Lists.newArrayList();
        String whereSql = getWhereSql(cond, params);
        if (StringUtils.isNotBlank(whereSql)) {
            sql.append(whereSql);
        }
        if (StringUtils.isNotBlank(orderBy)) {
            sql.append(" order by ").append(orderBy).append(isAsc ? " asc " : " desc ");
        }
        sql.append(" limit ").append(firstResult).append(",").append(pageSize);
        List<T> result = JdbcUtils.executeQueryForList(sql.toString(), params, resultType);
        if (StringUtils.isBlank(orderBy)) {
            return new Page(pageNum, pageSize, result, count);
        }
        return new Page(pageNum, pageSize, result, count, orderBy, isAsc);
    }

    public long getCount() {
        return getCount(null);
    }

    public long getCount(BaseCond cond) {
        StringBuilder sql = new StringBuilder("select count(*) from ");
        sql.append(getTableName());
        List list = Lists.newArrayList();
        String whereSql = cond != null ? getWhereSql(cond, list) : "";
        if (StringUtils.isNotBlank(whereSql)) {
            sql.append(whereSql);
        }
        if (CollectionUtils.isEmpty(list)) {
            return JdbcUtils.executeQueryForObject(sql.toString(), long.class);
        }
        return JdbcUtils.executeQueryForObject(sql.toString(), list, long.class);
    }

    public <T> List<T> findList(BaseCond cond, Class<T> requiredType) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(getTableName());
        List list = Lists.newArrayList();
        String whereSql = cond != null ? getWhereSql(cond, list) : "";
        if (StringUtils.isNotBlank(whereSql)) {
            sql.append(whereSql);
        }
        if (CollectionUtils.isEmpty(list)) {
            return JdbcUtils.executeQueryForList(sql.toString(), requiredType);
        }
        return JdbcUtils.executeQueryForList(sql.toString(), list, requiredType);
    }

    protected abstract String getTableName();

    protected static List<String> getObjectFieldNames(Class clazz) {
        return getObjectFieldNames(clazz, true);
    }

    protected static List<String> getObjectFieldNames(Class clazz, boolean skipId) {
        List<String> fieldNames = Lists.newArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if ("id".equalsIgnoreCase(fieldName) && skipId) {
                continue;
            }
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    protected static String getObjectFieldNamesSql(Class clazz) {
        return getObjectFieldNamesSql(clazz, true);
    }

    protected static String getObjectFieldNamesSql(Class clazz, boolean skipId) {
        List<String> fieldNames = getObjectFieldNames(clazz, skipId);
        return " " + fieldNames.toString().replace("[", "(").replace("]", ")") + " ";
    }

    protected static String getObjectFieldNamesPreparedSql(Class clazz) {
        return getObjectFieldNamesPreparedSql(clazz, true);
    }

    protected static String getObjectFieldNamesPreparedSql(Class clazz, boolean skipId) {
        List<String> fieldNames = getObjectFieldNames(clazz, skipId);
        StringBuilder sb = new StringBuilder("(?");
        for (int i = 1; i < fieldNames.size(); i++) {
            sb.append(",?");
        }
        return " " + sb.toString() + ") ";
    }

    protected static String getUpdateObjectFieldNamesSql(Class clazz) {
        List<String> fieldNames = getObjectFieldNames(clazz);
        StringBuilder sb = new StringBuilder(fieldNames.get(0) + " = ?");
        for (int i = 1; i < fieldNames.size(); i++) {
            sb.append(", " + fieldNames.get(i) + " = ?");
        }
        return " " + sb.toString() + " ";
    }

    protected List<List> objectToParamLists(Object obj, List<String> fieldNames) {
        List<List> lists = Lists.newArrayList();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int index = fieldNames.indexOf(fieldName);
            if (index < 0) {
                continue;
            }
            List list = Lists.newArrayList();
            Object fieldValue;
            try {
                fieldValue = PropertyUtils.getProperty(obj, field.getName());
            } catch (Exception e) {
                log.error("objectToParamLists getProperty error, field:{}", field.getName());
                throw new DaoException("objectToParamLists getProperty error", e);
            }
            Class javaType = field.getType();
            list.add(fieldValue);
            list.add(DefaultTypeRegistry.getInstance().getJdbcTypeByJava(javaType));
            list.add(javaType);
            lists.add(index, list);
        }
        return lists;
    }

    private static String getWhereSql(BaseCond cond) {
        return getWhereSql(cond, null);
    }

    private static String getWhereSql(BaseCond cond, List params) {
        Field[] fields = cond.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        if (ArrayUtils.isNotEmpty(fields)) {
            sb.append(" where 1=1 ");
            for (Field field : fields) {
                try {
                    String fieldName = field.getName();
                    Object fieldValue = PropertyUtils.getProperty(cond, fieldName);
                    if (fieldValue == null) {
                        continue;
                    }
                    if (field.getType() == List.class) {
                        List list = (List) fieldValue;
                        if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
                            if (params == null) {
                                throw new DaoException("baseDao getWhereSql params is null");
                            }
                            sb.append(" and ").append(fieldName + " >= ? and " + fieldName + " < ? ");
                            params.add(list.get(0));
                            params.add(list.get(1));
                        }
                        continue;
                    }
                    sb.append(" and ").append(fieldName + " = " + fieldValue);
                } catch (Exception e) {
                    log.error("getFindPageParams error", e);
                    throw new DaoException("getFindPageParams error", e);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getObjectFieldNamesSql(UserInfo.class));
        System.out.println(getUpdateObjectFieldNamesSql(UserInfo.class));
        System.out.println(getObjectFieldNamesPreparedSql(UserInfo.class));
        NoteCond cond = new NoteCond();
        cond.setCreateTime(new ArrayList() {{
            add(new Date());
            add(new Date());
        }});
        System.out.println(getWhereSql(cond));
    }
}
