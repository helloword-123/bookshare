package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.AuthenticationDTO;
import com.jie.bookshare.entity.dto.UserDTO;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.service.UserService;
import com.jie.bookshare.utils.JwtUtil;
import com.jie.bookshare.utils.WxUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private IRedisService redisService;


    /*
     * @Author Haojie
     * @Description 微信登录后，修改用户头像和昵称
     * @Param
     * @return
     **/
    @ApiOperation(value = "微信登录后，修改用户头像和昵称")
    @PostMapping("updateUserInfo")
    public Result updateAvatarAndName(@RequestBody UserDTO userDTO) {

        userService.updateUserInfo(userDTO);

        return Result.ok();
    }

    /*
     * @Author Haojie
     * @Description 管理员退出登录
     * @Param
     * @return
     **/
    // @PreAuthorize("hasAuthority('admin:auth')")
    @ApiOperation("管理员退出登录")
    @GetMapping("/logout/{userId}")
    public Result logout(@PathVariable Integer userId) {
        //删除登录状态
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
//        String userId = userDetails.getUsername();
        String key = IRedisService.concatKey("user_details", String.valueOf(userId));
        redisService.delete(key);
        return Result.ok();
    }

    /*
     * @Author Haojie
     * @Description 后台账号登录
     * @Param
     * @return
     **/
    @ApiOperation(value = "后台登录")
    @PostMapping("login")
    public Result login(
            @ApiParam(value = "登录的账号密码", required = true)
            @RequestBody AuthenticationDTO authenticationDTO) {
        String username = authenticationDTO.getUsername();
        String password = authenticationDTO.getPassword();
        List<String> info = userService.login(username, password); //info是{token,rId}
        if (info == null) {
            return Result.error().message("账号密码不匹配！");
        }
        return Result.ok().data("token", info.get(0)).data("id", info.get(1));
    }


    /*
     * @Author Haojie
     * @Description 小程序登录根据code获取token，并且请求openid与本地账号系统绑定，最后返回用户信息和token
     * @Param
     * @return
     **/
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

                return Result.ok().data("token", token).data("openid",openid).data("userinfo", userDTO);
            } else {
                return Result.error().message((String) map.get("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("未知错误");
        }
    }

    /*
     * @Author Haojie
     * @Description 验证token有效性
     * @Param
     * @return
     **/
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

