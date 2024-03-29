package com.jie.bookshare.common;

/**
 * @ClassName CommonResultCode
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/3/19 19:20
 */
public interface ResultCode {
    // 返回码
    Integer CODE_SUCCESS = 20000;    // 成功
    Integer CODE_ERROR = 20001;      // 失败
    Integer CODE_TOKEN_EXPIRE = 20002;      // token过期
    Integer CODE_ACCESS_LIMIT = 20003;      // 访问次数过多
    Integer CODE_ACCESS_DENIED = 20004;     // 权限不足

    // 返回消息
    String MESSAGE_ERROR = "服务器出错啦！";
    String MESSAGE_TOKEN_EXPIRE = "token过期！";
    String MESSAGE_ACCESS_LIMIT = "访问次数过多！";
    String MESSAGE_REPEAT_SUBMIT = "请勿重复提交！";
    String MESSAGE_ACCESS_DENIED = "权限不足！";
}

