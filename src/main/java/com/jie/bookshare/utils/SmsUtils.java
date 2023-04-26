package com.jie.bookshare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Date: 2023/1/29
 * Author: chenanyi
 */
@Component
public class SmsUtils {
    private static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    private static final String URL_TEMPLATE = "https://jmsms.market.alicloudapi.com/sms/send?mobile=%s&templateId=%s&value=%s";
    private static final String APP_CODE = "a3c8e7b5102b46038b2d6d34ae99f269";
    private static final String TAMPLATE_ID = "M105EABDEC";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送验证码
     * @param phone
     * @param code
     */
    public void send(String phone, String code) {
        // 构造url， 发起请求
        String url = String.format(URL_TEMPLATE, phone, TAMPLATE_ID, code);
        logger.info("sms request url: {}.", url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + APP_CODE);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("发送短信成功,响应信息: " + response.getBody());
            redisTemplate.opsForValue().set(phone, code);
            redisTemplate.expire(phone, 60, TimeUnit.SECONDS);
        } else {
            logger.info("发送短信失败,响应信息: " + response.getBody());
        }
    }

    /**
     * 验证手机号验证码
     * @param phone
     * @param code
     * @return
     */
    public int verify(String phone, String code){
        String verifyCode = (String) redisTemplate.opsForValue().get(phone);
        // 验证码过期
        if (verifyCode == null) {
            return 1;
        }
        // 验证码不一致
        if(!verifyCode.equals(code)){
            return 2;
        }
        return 0;
    }
}