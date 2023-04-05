package com.jie.bookshare.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @ClassName Authentication
 * @Description 后台登陆接收的用户密码类
 * @Author wuhaojie
 * @Date 2022/4/23 20:17
 */
@Data
public class AuthenticationDTO {
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^[\\w\\u4e00-\\u9fa5]{5,18}$", message = "昵称格式不对")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
