package com.jie.bookshare.filter.ddos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@Component
public class IpUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //操作k-v都是字符串的
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // @description: 如果通过了多级反向代理的话，
    // X-Forwarded-For的值并不止一个， 而是一串IP值，
    // 究竟哪个才是真正的用户端的真实IP呢？
    // 答案是取 X-Forwarded-For中第一个非unknown的有效IP字符串。
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    if (inet != null) {
                        ipAddress = inet.getHostAddress();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    public Boolean redisIP(HttpServletRequest request, int seconds, int maxCount) {
        String ip = this.getIpAddr(request);
        String key = "IP:" + ip;
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.opsForValue().increment(key);
            int count = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            if (count >= maxCount) {
                //IP访问次数超过，防止洪水攻击
                logger.error("IP: {}, 在{}秒内，访问次数为{}, 超过限制{}, 拒绝访问!", ip, seconds, count, maxCount);
                return false;
            }
        } else {
            stringRedisTemplate.opsForValue().set(key, "1");
            stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
        //正常访问
        return true;
    }
}

