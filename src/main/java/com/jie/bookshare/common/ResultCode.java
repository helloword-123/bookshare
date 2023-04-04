package com.jie.bookshare.common;

/**
 * @ClassName CommonResultCode
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/3/19 19:20
 */
public interface ResultCode {
    // 返回码
    Integer SUCCESS = 20000;    // 成功
    Integer ERROR = 20001;      // 失败

    // 返回消息
    String ERROR_MESSAGE = "服务器出错啦！";
}

