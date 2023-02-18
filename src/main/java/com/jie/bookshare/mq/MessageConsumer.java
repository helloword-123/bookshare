package com.jie.bookshare.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class MessageConsumer {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public static final String MESSAGE_KEY = "mq:";

    // 阻塞时间
    private Integer BLOCK_TIME = 10;


    public Long getMQSize(String msgNameSuffix){
        return redisTemplate.opsForList().size(MESSAGE_KEY + msgNameSuffix);
    }

    public List<MQMessage> rPop(String msgNameSuffix) {
        List<MQMessage> res = new ArrayList<>();
        Long msgSize = redisTemplate.opsForList().size(MESSAGE_KEY + msgNameSuffix);
        for(int i = 0;i < msgSize; ++i) {
            MQMessage message = (MQMessage) redisTemplate.opsForList().rightPop(MESSAGE_KEY + msgNameSuffix);
            res.add(message);
        }

        return res;
    }
}
