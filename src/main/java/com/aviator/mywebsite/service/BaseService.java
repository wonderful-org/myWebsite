package com.aviator.mywebsite.service;

import com.aviator.mywebsite.dao.MessageDao;
import com.aviator.mywebsite.dao.NoteDao;
import com.aviator.mywebsite.dao.UserDao;
import com.aviator.mywebsite.dao.UserInfoDao;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.exception.ControllerException;
import com.aviator.mywebsite.util.ResultUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public abstract class BaseService {

    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    protected UserDao userDao = new UserDao();

    protected UserInfoDao userInfoDao = new UserInfoDao();

    protected MessageDao messageDao = new MessageDao();

    protected NoteDao noteDao = new NoteDao();

    protected Result checkParams(Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            return ResultUtils.buildResult(ResultEnums.NULL_ARGUMENT);
        }
        for (Object param : params) {
            if (param == null) {
                return ResultUtils.buildResult(ResultEnums.NULL_ARGUMENT);
            }
            if (param instanceof String) {
                if (StringUtils.isBlank((String) param)) {
                    return ResultUtils.buildResult(ResultEnums.NULL_ARGUMENT);
                }
            }
        }
        return null;
    }

    protected <T> T convertFromDTO(Object originDTO, Class<T> deskClass) {
        try {
            T deskInstance = deskClass.newInstance();
            Field[] fields = originDTO.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == String.class) {
                    String fieldValue = (String) PropertyUtils.getProperty(originDTO, field.getName());
                    PropertyUtils.setProperty(deskInstance, field.getName(), StringUtils.isBlank(fieldValue) ? null : StringUtils.trimToNull(fieldValue));
                }
            }
            BeanUtils.copyProperties(deskInstance, originDTO);
            return deskInstance;
        } catch (Exception e) {
            log.error("convertFromDTO error", e);
            throw new ControllerException("convertFromDTO error", e);
        }
    }

    protected <T> T convertToDTO(Object origin, Class<T> deskDTOClass) {
        try {
            T deskDTO = deskDTOClass.newInstance();
            BeanUtils.copyProperties(deskDTO, origin);
            return deskDTO;
        } catch (Exception e) {
            log.error("convertToDTO error", e);
            throw new ControllerException("convertToDTO error", e);
        }
    }

}
