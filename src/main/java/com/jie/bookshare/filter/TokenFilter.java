package com.jie.bookshare.filter;


import com.jie.bookshare.entity.security.AppUserDetails;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.utils.JsonUtil;
import com.jie.bookshare.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IRedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //解析token
        String token = request.getHeader("token");
        String userId = JwtUtil.getUserId(token);
        //若token无效，则拒绝请求
        if (userId == null) {
            logger.error("Receive a request, but the token is wrong. Token is: {}.", token);
            filterChain.doFilter(request, response);
            return;
        }

        //从Redis中获取相应的登陆状态
        String key = IRedisService.concatKey("user_details", userId);
        String validToken = redisService.get(key, "token");
        //若在Redis中不存在相应的登陆状态，或者该token已经无效，则拒绝请求
        if (!token.equals(validToken)) {
            logger.error("Redis does not has this token: {}!", token);
            filterChain.doFilter(request, response);
            return;
        }
        String json = redisService.get(key, "user_details");
        AppUserDetails userDetails = JsonUtil.jsonToObject(json, AppUserDetails.class);
        if (userDetails == null) {
            logger.error("Redis does not has user_details!");
            filterChain.doFilter(request, response);
            return;
        }

        //把登陆状态存放到SecurityContext中，以便其他模块获取用户的登陆状态
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()));
        filterChain.doFilter(request, response);
    }
}
