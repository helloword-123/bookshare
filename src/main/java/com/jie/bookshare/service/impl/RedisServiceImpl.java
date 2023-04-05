package com.jie.bookshare.service.impl;

import com.jie.bookshare.service.IRedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    @Resource
    private StringRedisTemplate redis;

    @Override
    public Boolean delete(String k) {
        return redis.delete(k);
    }

    @Override
    public void setex(String k, String v, long timeout, TimeUnit timeUnit) {
        redis.opsForValue().set(k, v, timeout, timeUnit);
    }

    @Override
    public boolean exists(String k) {
        return Boolean.TRUE.equals(redis.hasKey(k));
    }

    /**
     * 当键不存在时返回null
     */
    @Override
    public String get(String k) {
        return redis.opsForValue().get(k);
    }

    @Override
    public void put(String key, String hashKey, String value) {
        redis.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void putAll(String key, Map<String, String> m) {
        redis.opsForHash().putAll(key, m);
    }

    @Override
    public String get(String key, String hashKey) {
        return (String) redis.opsForHash().get(key, hashKey);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redis.expire(key, timeout, unit);
    }
}
