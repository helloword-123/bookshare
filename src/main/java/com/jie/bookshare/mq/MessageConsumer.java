package com.jie.bookshare.mq;

import com.jie.bookshare.entity.Message;
import com.jie.bookshare.mapper.MessageMapper;
import com.jie.bookshare.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableScheduling
public class MessageConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private MessageMapper messageMapper;

    public static final String MESSAGE_KEY = "mq:";

    // 阻塞时间
    private final Integer BLOCK_TIME = 10;

    /**
     * 消息消费者，右弹出队列消息
     */
    @PostConstruct
    public void brPop() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if(current.isInterrupted()) {
                    // 断开连接
                    log.info("Redis consumer 正常断开连接！");
                    break;
                }

                try {
                    MQMessage mqMessage = (MQMessage) redisTemplate.opsForList().rightPop(MESSAGE_KEY, BLOCK_TIME,
                            TimeUnit.SECONDS);
                    if (mqMessage == null) {
                        continue;
                    }
                    logger.info("receive a message! msg:{}.", mqMessage);
                    // 插入数据库
                    Message msg = new Message();
                    msg.setProducerId(mqMessage.getProducerId());
                    msg.setHasConsumed(0);
                    try {
                        msg.setMessage(SerializeUtil.serializeObjToBytes(mqMessage));
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
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
                        logger.info("Send message to userId: {}.", mqMessage.getConsumerId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        });
        // 启动
        thread.setName("consumer");
        thread.start();
    }
}
