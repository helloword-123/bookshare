package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.Message;
import com.jie.bookshare.mapper.MessageMapper;
import com.jie.bookshare.mq.MQMessage;
import com.jie.bookshare.service.MessageService;
import com.jie.bookshare.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-02-18
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MessageMapper messageMapper;

    /**
     * 根据userId获取其已读和未读消息
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    //@Cacheable(value = "messages#10*60", key = "#userId")
    public List<List<MQMessage>> getAllMessages(Integer userId) throws Exception {
        logger.info("Get all messages by userId: {}.", userId);
        List<List<MQMessage>> res = new ArrayList<>();

        // 1. 获取所有未读消息
        LambdaQueryWrapper<Message> con1 = new LambdaQueryWrapper<>();
        con1.eq(Message::getConsumerId, userId)
                .eq(Message::getHasConsumed, 0)
                .orderByDesc(Message::getProduceTime);
        List<Message> unreadMessages = messageMapper.selectList(con1);
        List<MQMessage> mqMessages1 = new ArrayList<>();
        for (Message unreadMessage : unreadMessages) {
            MQMessage mqMessage = (MQMessage) SerializeUtil.deSerializeBytesToObj(unreadMessage.getMessage());
            mqMessage.setMsgId(unreadMessage.getId());
            mqMessages1.add(mqMessage);
        }

        // 2. 获取所有已读消息
        LambdaQueryWrapper<Message> con2 = new LambdaQueryWrapper<>();
        con2.eq(Message::getConsumerId, userId)
                .eq(Message::getHasConsumed, 1)
                .orderByDesc(Message::getProduceTime);
        List<Message> readMessages = messageMapper.selectList(con2);
        List<MQMessage> mqMessages2 = new ArrayList<>();
        for (Message readMessage : readMessages) {
            MQMessage mqMessage = (MQMessage) SerializeUtil.deSerializeBytesToObj(readMessage.getMessage());
            mqMessage.setMsgId(readMessage.getId());
            mqMessages2.add(mqMessage);
        }


        res.add(mqMessages1);
        res.add(mqMessages2);
        logger.info("Messages is: {}.", res);

        return res;
    }

    /**
     * 获取未读消息长度
     *
     * @param userId
     * @return
     */
    @Override
    //@Cacheable(value = "unReadMessageSize#60", key = "#userId")
    public Integer getUnReadMessagesSize(Integer userId) {
        logger.info("Get unread messages by userId: {}.", userId);
        LambdaQueryWrapper<Message> con1 = new LambdaQueryWrapper<>();
        con1.eq(Message::getConsumerId, userId)
                .eq(Message::getHasConsumed, 0);

        return messageMapper.selectCount(con1);
    }

    /**
     * 已读消息
     *
     * @param userId
     * @param msgId
     */
    @Override
    public void readMessage(Integer userId, Integer msgId) {
        logger.info("UserId: {}, read a message ,msgId: {}.", userId, msgId);
        Message message = new Message();
        message.setId(msgId);
        message.setHasConsumed(1);
        messageMapper.updateById(message);
    }
}
