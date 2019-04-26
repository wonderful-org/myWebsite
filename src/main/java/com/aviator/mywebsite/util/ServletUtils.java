package com.aviator.mywebsite.util;

import com.aviator.mywebsite.exception.ControllerException;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName ServletUtils
 * @Author aviator_ls
 * @Date 2019/4/26 12:58
 */
public class ServletUtils {

    private static final Logger log = LoggerFactory.getLogger(ServletUtils.class);

    public static Map<String, Object> getParams(HttpServletRequest req) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            String valueStr = null;
            if (ArrayUtils.isNotEmpty(value)) {
                if (value.length > 1) {
                    log.warn("request parameter 1 key mapping value gt 1, param:{}", key);
                }
                valueStr = value[0];
            }
            result.put(key, valueStr);
        }
        return result;
    }

    public static <T> T getParams(HttpServletRequest req, Class<T> resultType) {
        Map<String, Object> result = getParams(req);
        try {
            T instance = resultType.newInstance();
            BeanUtils.populate(instance, result);
            return instance;
        } catch (Exception e) {
            log.error("request parameter populate error", e);
            throw new ControllerException("request parameter populate error", e);
        }
    }
}
