package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.security.AppUserDetails;
import com.jie.bookshare.entity.security.UserType;
import com.jie.bookshare.entity.vo.Authentication;
import com.jie.bookshare.entity.vo.UserVo;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.service.UserService;
import com.jie.bookshare.utils.JsonUtil;
import com.jie.bookshare.utils.JwtUtil;
import com.jie.bookshare.utils.SpringSecurityUtil;
import com.jie.bookshare.utils.WxUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@Api(tags = "账号相关")
public class AuthController {
    @Autowired
    private IRedisService redisService;
    @Autowired
    private UserService userService;


    @ApiOperation(value = "微信授权登录后，根据登录信息返回顾客id")
    @PostMapping("getUserIdByLoginInfo")
    public Result getUserIdByLoginInfo(@RequestHeader("token") String token,
                                @RequestBody UserVo userVo) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.USER)
            return Result.error().message("无权访问");
        //把顾客的ID保存到Redis中的登录信息里
        String userId = JwtUtil.getUserId(token);
        Integer cId = userService.getUserIdByLoginInfo(userVo);
        String key = IRedisService.concatKey("user_details", userId);
        userDetails.setUsername(String.valueOf(cId));
        redisService.put(key, "user_details", JsonUtil.toJson(userDetails));
        return Result.ok().data("cId", cId);
    }

    @PreAuthorize("hasAuthority('admin:auth')")
    @ApiOperation("管理员退出登录")
    @GetMapping("/adminLogout")
    public Result adminLogout() {
        //删除登录状态
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        String key = IRedisService.concatKey("user_details", userId);
        redisService.delete(key);
        return Result.ok();
    }

    @ApiOperation(value = "后台登录")
    @PostMapping("login")
    public Result login(
            @ApiParam(value = "登录的账号密码", required = true)
            @RequestBody Authentication authentication) {
        String username = authentication.getUsername();
        String password = authentication.getPassword();
        List<String> info = userService.login(username, password); //info是{token,rId}
        if (info == null) {
            return Result.error().message("账号密码不匹配！");
        }
        return Result.ok().data("token", info.get(0)).data("id", info.get(1));
    }

    @ApiOperation(value = "获取token", notes =
            "请求体包含一个code字段，其值为code的内容\n" +
                    "如果获取token成功，则响应体包含一个token字段，其值为token的内容"
    )
    @PostMapping("/token")
    public Result getToken(
            @ApiParam(value = "临时登录凭证code", required = true)
            @RequestBody Map<String, Object> reqBody) {
        String code = (String) reqBody.get("code");
        if (code == null) return Result.error().message("缺少code参数");

        try {
            Map<String, Object> map = WxUtil.code2Session(code);
            Integer errCode = (Integer) map.get("errcode");
            if (errCode == null) {
                String openId = (String) map.get("openid");
                String token = JwtUtil.generateToken(openId);
                //把登陆状态保存到Redis中，这时候还没有确定顾客的ID
                AppUserDetails userDetails = new AppUserDetails(
                        null, null, null, UserType.USER);
                String key = IRedisService.concatKey("user_details", openId);
                redisService.put(key, "token", token);
                redisService.put(key, "user_details", JsonUtil.toJson(userDetails));
                redisService.expire(key, 1, TimeUnit.DAYS);
                return Result.ok().data("token", token);
            } else {
                return Result.error().message((String) map.get("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("未知错误");
        }
    }

    @ApiOperation(value = "验证token有效性", notes =
            "返回数据含有一个isValid字段，如果token有效，则isValid字段为true，否则为false"
    )
    @GetMapping("/checkToken")
    public Result checkToken(
            @ApiParam(value = "被验证的token", required = true)
            @RequestHeader("token") String token) {
        String userId = JwtUtil.getUserId(token);
        if (userId == null)
            return Result.ok().data("isValid", false);
        //检查token跟Redis中保存的登录状态中的token是否一致
        String key = IRedisService.concatKey("user_details", userId);
        String validToken = redisService.get(key, "token");
        return Result.ok().data("isValid", token.equals(validToken));
    }
}
