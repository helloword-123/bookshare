package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.Message;
import com.jie.bookshare.mq.MQMessage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-02-18
 */
public interface MessageService extends IService<Message> {

    /**
     * 根据userId获取其已读和未读消息
     * @param userId
     * @return
     */
    List<List<MQMessage>> getAllMessages(Integer userId) throws Exception;

    /**
     * 获取未读消息长度
     * @param userId
     * @return
     */
    Integer getUnReadMessagesSize(Integer userId);

    /**
     * 已读消息
     * @param userId
     * @param msgId
     */
    void readMessage(Integer userId, Integer msgId);
}
