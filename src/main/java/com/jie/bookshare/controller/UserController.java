package com.jie.bookshare.controller;


import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.RedisKeys;
import com.jie.bookshare.common.Result;
import com.jie.bookshare.common.ResultCode;
import com.jie.bookshare.entity.AclRole;
import com.jie.bookshare.entity.User;
import com.jie.bookshare.entity.dto.AuthenticationDTO;
import com.jie.bookshare.entity.dto.UserDTO;
import com.jie.bookshare.filter.ddos.AccessLimit;
import com.jie.bookshare.filter.repeatsubmit.RepeatSubmit;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.service.UserService;
import com.jie.bookshare.utils.JwtUtil;
import com.jie.bookshare.utils.SmsUtils;
import com.jie.bookshare.utils.WxUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 用户Controller
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Validated
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private IRedisService redisService;
    @Resource
    private SmsUtils smsUtils;
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "发送验证码")
    @PreAuthorize("hasAuthority('user:query')")
    @GetMapping("/sendSmsCode/{phone}")
    public Result sendSmsCode(@PathVariable String phone) {
        // 随机生成4位验证码
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            verificationCode.append(random.nextInt(10));
        }
        try {
            smsUtils.send(phone, String.valueOf(verificationCode));
        } catch (Exception e) {
            logger.error("error in send sms code! message is: {}.", e.getMessage());
            return Result.error().message(ResultCode.MESSAGE_ERROR);
        }
        return Result.ok();
    }

    /**
     * 发送验证码
     * @param reqBody
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "验证手机号验证码")
    @PreAuthorize("hasAuthority('user:query')")
    @PostMapping("/verifySmsCode")
    public Result verifySmsCode(@RequestBody Map<String, Object> reqBody) {
        Integer userId = (Integer) reqBody.get("userId");
        String phone = (String) reqBody.get("phone");
        String code = (String) reqBody.get("code");
        // 验证
        int res = smsUtils.verify(phone, code);
        if(res == 2){
            logger.error("验证码不一致！");
            return Result.error().message("验证码不一致！");
        }
        if(res == 1){
            logger.error("验证码已过期！");
            return Result.error().message("验证码已过期！");
        }
        // 绑定手机号
        User user = new User();
        user.setId(userId);
        user.setPhone(phone);
        userService.update(user, null);

        return Result.ok();
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "获取用户信息")
    @PreAuthorize("hasAuthority('user:query')")
    @GetMapping("/getUserInfoByToken")
    public Result getUserInfoByUserId(
            @RequestHeader String token) {
        String userId = JwtUtil.getUserId(token);
        if (userId == null) {
            return Result.error().message("用户id不存在！");
        }
        UserDTO userDTO = userService.getUserInfoByUserId(userId);
        return Result.ok().data("userinfo", userDTO);
    }

    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "获取用户角色")
    @PreAuthorize("hasAuthority('user:query')")
    @GetMapping("getUserRoles/{userId}")
    public Result getUserRoles(
            @ApiParam(value = "用户id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer userId) {
        List<String> roles = new ArrayList<>();
        List<AclRole> aclRoles = userService.findRoleForAdmin(userId);
        for (AclRole role : aclRoles) {
            roles.add(role.getKey());
        }
        return Result.ok().data("roles", roles);
    }

    /**
     * 微信登录后，修改用户头像和昵称
     *
     * @param userDTO 用户头像和昵称（暂时不校验参数）
     * @return
     */
    @RepeatSubmit(expireSeconds = CommonConstant.EXPIRE_SECONDS, value = CommonConstant.AUTH)
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "微信登录后，修改用户头像和昵称")
    @PreAuthorize("hasAuthority('user:update')")
    @PostMapping("updateUserInfo")
    public Result updateAvatarAndName(@RequestBody UserDTO userDTO) {
        userService.updateUserInfo(userDTO);
        return Result.ok();
    }

    /**
     * 管理员退出登录
     *
     * @param userId 用户id
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation("管理员退出登录")
    @PreAuthorize("hasAuthority('user:update')")
    @GetMapping("/logout/{userId}")
    public Result logout(
            @ApiParam(value = "用户id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer userId) {
        String key = IRedisService.concatKey(RedisKeys.USER_INFO, String.valueOf(userId));
        redisService.delete(key);
        return Result.ok();
    }

    /**
     * 后台账号登录
     *
     * @param authenticationDTO 前端后台登录传输的dto
     * @return
     */
    @RepeatSubmit(expireSeconds = CommonConstant.EXPIRE_SECONDS, value = CommonConstant.AUTH)
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @Deprecated
    @ApiOperation(value = "后台登录")
    @PostMapping("login")
    public Result login(
            @ApiParam(value = "登录的账号密码", required = true)
            @Valid
            @RequestBody AuthenticationDTO authenticationDTO) {
        String username = authenticationDTO.getUsername();
        String password = authenticationDTO.getPassword();
        List<String> info = userService.login(username, password); //info是{token,rId}
        if (info == null) {
            return Result.error().message("账号密码不匹配！");
        }
        return Result.ok().data(CommonConstant.TOKEN, info.get(0)).data("id", info.get(1));
    }


    /**
     * 小程序登录根据code获取token，并且请求openid与本地账号系统绑定，最后返回用户信息和token
     *
     * @param reqBody 前端微信登录传输的dto
     * @return
     */
    @RepeatSubmit(expireSeconds = CommonConstant.EXPIRE_SECONDS, value = CommonConstant.AUTH)
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "微信小程序登录")
    @PostMapping("/wxLogin")
    public Result wxLogin(
            @ApiParam(value = "临时登录凭证code", required = true)
            @RequestBody Map<String, Object> reqBody) {
        String code = (String) reqBody.get("code");
        if (code == null) {
            return Result.error().message("缺少code参数");
        }
        try {
            Map<String, Object> map = WxUtil.code2Session(code);
            Integer errCode = (Integer) map.get("errcode");
            if (errCode == null) {
                String openid = (String) map.get("openid");
                //把登陆状态保存到Redis中
                UserDTO userDTO = userService.getUserInfoByOpenid(openid);
                String token = userService.saveUserAuthorityByUserId(userDTO.getId());

                return Result.ok().data(CommonConstant.TOKEN, token).data("userinfo", userDTO);
            } else {
                return Result.error().message((String) map.get("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("未知错误");
        }
    }

    /**
     * 验证token有效性
     *
     * @param token token值
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "验证token有效性", notes =
            "返回数据含有一个isValid字段，如果token有效，则isValid字段为true，否则为false"
    )
    @GetMapping("/checkToken")
    public Result checkToken(
            @ApiParam(value = "被验证的token", required = true, example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxIiwiZXhwIjoxNjgwNzU5MTc5LCJqdGkiOiI2YjFmYzJjNC1jNGI1LTQzMjMtOWEzMi1kNTkzZDM5NjNmMDYifQ.I5zDOIt23PT19h-uUNkDG6NPtSq2k3DJywBwgACgO7E")
            @RequestHeader(CommonConstant.TOKEN) String token) {
        String userId = JwtUtil.getUserId(token);
        if (userId == null)
            return Result.ok().data("isValid", false);
        //检查token跟Redis中保存的登录状态中的token是否一致
        String key = IRedisService.concatKey(RedisKeys.USER_INFO, userId);
        String validToken = redisService.get(key, CommonConstant.TOKEN);
        return Result.ok().data("isValid", token.equals(validToken));
    }
}

