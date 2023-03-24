package com.jie.bookshare.mq;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MESSAGE_KEY = "mq:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 消息提供，左推送消息到队列
     *
     * @param msg 消息
     */
    public void lPush(MQMessage msg) {
        new Thread(() -> {
            Long size = redisTemplate.opsForList().leftPush(MESSAGE_KEY, msg);
            logger.info("Push msg to mq, mqKey is: {}, msg is: {}.", MESSAGE_KEY, msg);
        }).start();
    }
}
