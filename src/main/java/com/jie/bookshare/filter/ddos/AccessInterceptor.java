package com.jie.bookshare.filter.ddos;

import com.jie.bookshare.common.Result;
import com.jie.bookshare.common.ResultCode;
import com.jie.bookshare.utils.JsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Resource
    IpUtils ipUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            //请求的方法是否带有accessLimit注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            if (ipUtils.redisIP(
                    request,
                    accessLimit.seconds(),
                    accessLimit.maxCount(),
                    hm.getMethod().getDeclaringClass() + ":" + hm.getMethod().getName())) {
                // 一切正常
                return true;
            } else {
                // 返回
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.print(JsonUtil.toJson(Result.error().code(ResultCode.CODE_ACCESS_LIMIT).message(ResultCode.MESSAGE_ACCESS_LIMIT)));
                writer.flush();
                writer.close();

                return false;
            }
        }

        return true;
    }
}
