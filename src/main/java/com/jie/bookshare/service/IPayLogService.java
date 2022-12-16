package com.jie.bookshare.service;

import java.util.Map;

public interface IPayLogService {

    //创建：这里使用了别人的请求账号
    Map<String,Object> createNative(String orderNo, String note);

    //查询支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //更新支付状态
    void updateOrderStatus(Map<String, String> map);
}
