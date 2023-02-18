package com.jie.bookshare.mq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列的统一消息体
 */
@Data
public class MQMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息类型id")
    private Integer msgId;
    @ApiModelProperty(value = "发送者id")
    private Integer producerId;
    @ApiModelProperty(value = "接收者id")
    private Integer consumerId;
    @ApiModelProperty(value = "发送消息时间")
    private Date produceTime;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();



    MQMessage(){}

    public MQMessage(Integer msgId, Integer producerId, Integer consumerId, Date produceTime) {
        this.msgId = msgId;
        this.producerId = producerId;
        this.consumerId = consumerId;
        this.produceTime = produceTime;
    }


    public void addData(String key, Object value){
        this.data.put(key, value);
    }

    public MQMessage data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    public MQMessage data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
