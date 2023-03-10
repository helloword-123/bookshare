package com.jie.bookshare.mq;

import com.aliyun.oss.model.CnameConfiguration;
import com.jie.bookshare.entity.Message;
import com.jie.bookshare.mapper.MessageMapper;
import com.jie.bookshare.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableScheduling
public class MessageConsumer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private MessageMapper messageMapper;

    public static final String MESSAGE_KEY = "mq:";

    // 阻塞时间
    private final Integer BLOCK_TIME = 10;


    @PostConstruct
    public void brPop() {
        new Thread(() -> {
            while (true) {
                MQMessage mqMessage = (MQMessage) redisTemplate.opsForList().rightPop(MESSAGE_KEY, BLOCK_TIME,
                        TimeUnit.SECONDS);
                if (mqMessage == null) {
                    continue;
                }
                log.info("receive a message! msg:{}.", mqMessage);
                // 插入数据库
                Message msg = new Message();
                msg.setProducerId(mqMessage.getProducerId());
                msg.setHasConsumed(0);
                try {
                    msg.setMessage(SerializeUtil.serializeObjToBytes(mqMessage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                msg.setConsumerId(mqMessage.getConsumerId());
                messageMapper.insert(msg);

                // 通知用户
                WebSocketServer wss = WebSocketServer.getWebSocketServerByUserId(String.valueOf(mqMessage.getConsumerId()));
                if (wss == null) {
                    continue;
                }
                try {
                    wss.sendMessage("你有新的消息！");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
