package com.jie.bookshare.filter.token;


import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.RedisKeys;
import com.jie.bookshare.common.Result;
import com.jie.bookshare.common.ResultCode;
import com.jie.bookshare.entity.security.AppUserDetails;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.utils.JsonUtil;
import com.jie.bookshare.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class TokenFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IRedisService redisService;

    @Override
    public void doFilter(ServletRequest request,
                            ServletResponse response,
                            FilterChain filterChain) throws ServletException, IOException {
        // 解析token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(CommonConstant.TOKEN);
        String userId = JwtUtil.getUserId(token);
        // 若token无效，则跳过
        if (userId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从Redis中获取相应的登陆状态
        String key = IRedisService.concatKey(RedisKeys.USER_INFO, userId);
        String validToken = redisService.get(key, CommonConstant.TOKEN);
        // 若在Redis中不存在相应的登陆状态，或者该token已经无效，则拒绝请求
        if (!token.equals(validToken)) {
            logger.error("Redis does not has this token: {}!", token);
            // 返回
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(JsonUtil.toJson(Result.error().code(ResultCode.CODE_TOKEN_EXPIRE).message(ResultCode.MESSAGE_TOKEN_EXPIRE)));
            writer.flush();
            writer.close();

            return;
        }
        String json = redisService.get(key, RedisKeys.USER_INFO);
        AppUserDetails userDetails = JsonUtil.jsonToObject(json, AppUserDetails.class);
        if (userDetails == null) {
            logger.error("Redis does not has user_details!");
            // 返回
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(JsonUtil.toJson(Result.error() .code(ResultCode.CODE_TOKEN_EXPIRE).message(ResultCode.MESSAGE_TOKEN_EXPIRE)));
            writer.flush();
            writer.close();

            return;
        }

        // 把登陆状态存放到SecurityContext中，以便其他模块获取用户的登陆状态
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()));
        filterChain.doFilter(request, response);
    }
}
