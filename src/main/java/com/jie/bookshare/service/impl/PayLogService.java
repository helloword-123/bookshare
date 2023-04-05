package com.jie.bookshare.service.impl;

/**
 * @ClassName PayLogService
 * @Description 微信支付
 * @Author wuhaojie
 * @Date 2021/12/22 10:28
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.jie.bookshare.service.IPayLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PayLogService implements IPayLogService {
//    @Resource
//    private OrderMapper orderMapper;
//
//    @Resource
//    private OrderDetailsMapper orderDetailsMapper;

    //请求微信支付二维码
    @Override
    public Map<String,Object> createNative(String orderNo, String note) {
//        try {
//            // 增加订单备注
//            OrderDetails ods = new OrderDetails();
//            ods.setNote(note);
//            orderDetailsMapper.update(ods, new LambdaQueryWrapper<OrderDetails>().eq(OrderDetails::getOId,orderNo));
//
//
//            Order order = orderMapper.selectById(orderNo);
//            Map<String,String> m = new HashMap<>();
//            m.put("appid", "wx74862e0dfcf69954");
//            m.put("mch_id", "1558950191");
//            m.put("nonce_str", WXPayUtil.generateNonceStr());
//            m.put("body", order.getOId()+"");
//            m.put("out_trade_no", orderNo);
//
//            //!!!这里测试将订单金额固定为0.01
//            m.put("total_fee", BigDecimal.valueOf(0.01).multiply(new BigDecimal("100")).longValue()+"");
//
//            m.put("spbill_create_ip", "112.74.41.146");
//            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
//            m.put("trade_type", "NATIVE");
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
//            client.setHttps(true);
//            client.post();
//            String xml = client.getContent();
//            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//            System.out.println(resultMap);
//
//            Map<String,Object> map = new HashMap<>();
//            map.put("out_trade_no", orderNo);
//            map.put("order_id", order.getOId());
//            map.put("total_fee", order.getOPrice());
//            map.put("result_code", resultMap.get("result_code"));
//            map.put("code_url", resultMap.get("code_url"));
//            return map;
//        } catch (Exception var9) {
//            var9.printStackTrace();
//            return new HashMap<>();
//        }
        return null;
    }

    //查询支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
//        try {
//            Map<String,String> m = new HashMap<>();
//            m.put("appid", "wx74862e0dfcf69954");
//            m.put("mch_id", "1558950191");
//            m.put("out_trade_no", orderNo);
//            m.put("nonce_str", WXPayUtil.generateNonceStr());
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
//            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
//            client.setHttps(true);
//            client.post();
//            String xml = client.getContent();
//            return WXPayUtil.xmlToMap(xml);
//        } catch (Exception var6) {
//            var6.printStackTrace();
//            return null;
//        }
        return null;
    }

    //更新支付状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
//        String orderNo = map.get("out_trade_no");
//        Order order = orderMapper.selectById(orderNo);
//        if (order.getOState() != 1) {
//            order.setOState(1);
//            orderMapper.updateById(order);
//        }
    }
}
