package com.jie.bookshare.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {

    public static final String MESSAGE_KEY = "mq:";

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    public void lPush(String msgNameSuffix, MQMessage msg) {
        new Thread(() -> {
            Long size = redisTemplate.opsForList().leftPush(MESSAGE_KEY + msgNameSuffix, msg);
        }).start();
    }
}
