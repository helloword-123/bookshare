package com.jie.bookshare.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * JSON转换工具类
 */
public class JsonUtil {
    private static final ObjectMapper OM = new ObjectMapper();

    /**
     * 转换Json字符串为Map
     *
     * @param json JSON字符串
     * @return map
     */
    public static Map<String, Object> JsonToMap(String json) {
        try {
            return OM.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 转换对象为Json字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        try {
            return OM.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 转换Json字符串为对象
     *
     * @param json  Json字符串
     * @param clazz 对象类型
     * @return 对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return OM.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
