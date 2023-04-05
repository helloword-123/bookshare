package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.mq.MQMessage;
import com.jie.bookshare.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import java.util.List;

/**
 * 消息Controller
 *
 * @author wuhaojie
 * @since 2023-02-18
 */
@Validated
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 获取未读消息长度
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "获取未读消息长度")
    @GetMapping("/getUnReadMessagesSize/{userId}")
    public Result getUnReadMessagesSize(@Digits(integer = 3, fraction = 0)
                                        @PathVariable Integer userId) {
        Integer msgSize = messageService.getUnReadMessagesSize(userId);
        return Result.ok().data("msgSize", msgSize);
    }

    /**
     * 根据userId获取其已读和未读消息
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "根据userId获取其已读和未读消息")
    @GetMapping("/getAllMessages/{userId}")
    public Result getAllMessages(@Digits(integer = 3, fraction = 0)
                                 @PathVariable Integer userId) {
        List<List<MQMessage>> messageList = null;
        try {
            messageList = messageService.getAllMessages(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok().data("messageList", messageList);
    }

    /**
     * 已读消息
     *
     * @param userId 用户id
     * @param msgId  消息id
     * @return
     */
    @ApiOperation(value = "读取消息")
    @GetMapping("/readMessage/{userId}/{msgId}")
    public Result readMessage(@Digits(integer = 3, fraction = 0)
                              @PathVariable Integer userId,
                              @Digits(integer = 3, fraction = 0)
                              @PathVariable Integer msgId) {
        messageService.readMessage(userId, msgId);
        return Result.ok();
    }
}
