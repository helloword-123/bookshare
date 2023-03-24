package com.jie.bookshare.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface IRedisService {

    static String concatKey(String... keys) {
        int n = keys.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i != 0) sb.append(":");
            sb.append(keys[i]);
        }
        return sb.toString();
    }

    void setex(String k, String v, long timeout, TimeUnit timeUnit);

    boolean exists(String k);

    String get(String k);

    void put(String key, String hashKey, String value);

    void putAll(String key, Map<String, String> m);

    String get(String key, String hashKey);

    Map<String, String> getAll(String key);

    Boolean expire(String key, long timeout, TimeUnit unit);

    Boolean delete(String k);
}
