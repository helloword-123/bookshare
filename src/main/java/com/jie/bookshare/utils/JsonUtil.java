package com.jie.bookshare.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * JSON转换工具类
 */
public class  JsonUtil {
    private static final ObjectMapper OM = new ObjectMapper();

    public static Map<String, Object> JsonToMap(String json) {
        try {
            return OM.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static void setInterceptorResponse(HttpServletResponse response,
                                              Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] json = objectMapper.writeValueAsBytes(data);
        OutputStream out = response.getOutputStream();
        out.write(json);
        out.close();
    }

    public static String toJson(Object obj) {
        try {
            return OM.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return OM.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
