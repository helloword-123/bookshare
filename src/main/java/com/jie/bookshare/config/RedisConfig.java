package com.jie.bookshare.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;
import java.util.Map;

@EnableCaching // 使用了CacheManager，别忘了开启它  否则无效
@Configuration
public class RedisConfig {

    /**
     * 生成缓存管理者
     *
     * @param redisConnectionFactory redis连接工厂
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1));
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        return new MyRedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    /**
     * redisTemplate配置
     *
     * @param factory redis连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jacksonSerializer.setObjectMapper(ObjectMapperConfig.objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 使用StringRedisSerializer来序列化和反序列化redis的key,value采用json序列化
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jacksonSerializer);

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jacksonSerializer);
        template.afterPropertiesSet();

        return template;
    }
}

/**
 * 通过cacheName自定义过期时间的RedisCacheManager
 * 支持直接使用cacheName来定义过期时间
 * cacheName：name#time   time为过期时间，单位秒，0为不过期
 * 例如：test#100  意思是定义一个名为test#100的缓存，且过期时间为100秒
 */
class MyRedisCacheManager extends RedisCacheManager {

    /**
     * 缓存参数的分隔符
     * 数组元素0=缓存的名称
     * 数组元素1=缓存过期时间TTL
     */
    private static final String DEFAULT_SEPARATOR = "#";

    /**
     * 默认的key前缀
     */
    private static final CacheKeyPrefix DEFAULT_CACHE_KEY_PREFIX = cacheName -> cacheName + ":";
    /**
     * 默认序列化方式为json
     */
    private static final RedisSerializationContext.SerializationPair<String> DEFAULT_PAIR = RedisSerializationContext.SerializationPair
            .fromSerializer(new Jackson2JsonRedisSerializer<>(String.class));

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
    }

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
    }

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
    }

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    /**
     * 针对@Cacheable设置缓存过期时间
     *
     * @param name        @Cacheable、@CachePut和@CacheEvit注解的value/cacheNames的值
     * @param cacheConfig 缓存配置
     * @return RedisCache
     */
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        //根据分隔符拆分字符串，并进行过期时间ttl的解析
        String[] cacheParams = StringUtils.delimitedListToStringArray(name, DEFAULT_SEPARATOR);
        name = cacheParams[0];
        if (cacheParams.length > 1) {
            Integer ttl;
            try {
                // JS引擎计算字符串表达式
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                ttl = (Integer) engine.eval(cacheParams[1]);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
            cacheConfig = cacheConfig
                    .entryTtl(Duration.ofSeconds(ttl))
                    .computePrefixWith(DEFAULT_CACHE_KEY_PREFIX)
                    .serializeValuesWith(DEFAULT_PAIR);
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
