package com.jie.bookshare.filter.repeatsubmit;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.ResultCode;
import com.jie.bookshare.exception.CustomizeRuntimeException;
import com.jie.bookshare.filter.ddos.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Aspect
public class NoRepeatSubmitAspect {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.jie.bookshare.filter.repeatsubmit.RepeatSubmit)")
    public void preventDuplication() {}

    @Around("preventDuplication()")
    public Object around(ProceedingJoinPoint joinPoint) {

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        // 获取执行方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // 获取防重复提交注解
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);

        // 获取方法标记，生成redisKey和redisValue
        String url = request.getRequestURI();

        // 获取请求ip
        IpUtils ipUtils = new IpUtils();
        String ip = ipUtils.getIpAddr(request);

        /*
         *  通过前缀 + url + 函数参数签名 来生成redis上的 key
         */
        String redisKey = CommonConstant.PREVENT_DUPLICATION_PREFIX
                .concat(ip)
                .concat("_")
                .concat(url)
                .concat("_")
                .concat(getMethodSign(method, joinPoint.getArgs()));
        // 这个值只是为了标记，不重要
        String redisValue = redisKey.concat(annotation.value());

        if (!redisTemplate.hasKey(redisKey)) {
            // 设置防重复操作限时标记（前置通知）
            redisTemplate.opsForValue()
                    .set(redisKey, redisValue, annotation.expireSeconds(), TimeUnit.SECONDS);
            try {
                // 正常执行方法并返回
                // ProceedingJoinPoint类型参数可以决定是否执行目标方法，
                // 且环绕通知必须要有返回值，返回值即为目标方法的返回值
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                // 确保方法执行异常实时释放限时标记(异常后置通知)
                redisTemplate.delete(redisKey);
                throw new CustomizeRuntimeException(throwable.getMessage());
            }
        } else {
            // 重复提交了抛出异常，如果是在项目中，根据具体情况处理。
            // 注意，这是运行时异常！
            throw new CustomizeRuntimeException(ResultCode.MESSAGE_REPEAT_SUBMIT);
        }


    }

    /**
     * 生成方法标记：采用数字签名算法SHA1对方法签名字符串加签
     *
     * @param method
     * @param args
     * @return
     */
    private String getMethodSign(Method method, Object... args) {
        StringBuilder sb = new StringBuilder(method.toString());
        for (Object arg : args) {
            sb.append(toString(arg));
        }
        return DigestUtil.sha1Hex(sb.toString());
    }

    private String toString(Object arg) {
        if (Objects.isNull(arg)) {
            return "null";
        }
        if (arg instanceof Number) {
            return arg.toString();
        }
        return JSONUtil.toJsonStr(arg);
    }

}

