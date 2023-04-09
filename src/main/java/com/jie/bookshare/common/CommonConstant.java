package com.jie.bookshare.common;

public class CommonConstant {
    // 请求次数限制
    public static final int REQUEST_SECONDS = 5;
    public static final int REQUEST_MAX_COUNT = 5;

    // 防止重复提交
    public static final int EXPIRE_SECONDS = 5;
    
    public static final String TOKEN = "token";

    public static final String AUTH = "Authorization";

    public static final String PREVENT_DUPLICATION_PREFIX = "PREVENT_DUPLICATION_PREFIX:";

}
