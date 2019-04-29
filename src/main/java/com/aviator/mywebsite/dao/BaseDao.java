package com.aviator.mywebsite.dao;

import com.aviator.mywebsite.db.JdbcUtils;
import com.aviator.mywebsite.db.type.DefaultTypeRegistry;
import com.aviator.mywebsite.entity.po.UserInfo;
import com.aviator.mywebsite.exception.DaoException;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public abstract class BaseDao {

    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);

    protected static final String USER_TABLE_NAME = "mw_user";

    protected static final String USER_INFO_TABLE_NAME = "mw_user_info";

    protected static final String MESSAGE_TABLE_NAME = "mw_message";

    protected static final String NOTE_TABLE_NAME = "mw_note";

    public <T> long insert(T obj) {
        Class objType = obj.getClass();
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(getTableName()).append(getObjectFieldNamesSql(objType)).append(" values ").append(getObjectFieldNamesPreparedSql(objType));
        return JdbcUtils.executeInsert(sql.toString(), objectToParamLists(obj, getObjectFieldNames(objType)), long.class);
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

    public long getCount() {
        StringBuilder sql = new StringBuilder("select count(*) from ");
        sql.append(getTableName());
        return JdbcUtils.executeQueryForObject(sql.toString(), long.class);
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
        List<String> fieldNames = getObjectFieldNames(clazz);
        return " " + fieldNames.toString().replace("[", "(").replace("]", ")") + " ";
    }

    protected static String getObjectFieldNamesPreparedSql(Class clazz) {
        List<String> fieldNames = getObjectFieldNames(clazz);
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

    public static void main(String[] args) {
        System.out.println(getObjectFieldNamesSql(UserInfo.class));
        System.out.println(getUpdateObjectFieldNamesSql(UserInfo.class));
        System.out.println(getObjectFieldNamesPreparedSql(UserInfo.class));
    }
}
