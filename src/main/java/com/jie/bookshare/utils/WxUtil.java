package com.jie.bookshare.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * 微信开放平台接口调用
 */
public class WxUtil {
    private static final String APP_ID = "wx64e73796f64380c0";
    private static final String APP_SECRET = "64f86e32405825b44feaa222e0bce6f7";
    private static final String BASE_URL = "https://api.weixin.qq.com/sns/";

    /**
     * 登录凭证校验
     */
    public static Map<String, Object> code2Session(String code) throws IOException, ParseException {
        HttpClient httpClient = new HttpClient(BASE_URL + "jscode2session");
        httpClient.addParameter("appid", APP_ID);
        httpClient.addParameter("secret", APP_SECRET);
        httpClient.addParameter("js_code", code);
        httpClient.addParameter("grant_type", "authorization_code");
        httpClient.get();
        return JsonUtil.JsonToMap(httpClient.getContent());
    }
}
