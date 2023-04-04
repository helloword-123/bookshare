package com.jie.bookshare.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class AuthDTO {
    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotBlank(message = "学好/工号不能为空")
    private String number;

    @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5]{2,10}(·[\\u4E00-\\u9FA5]{2,10}){0,2}$", message = "姓名格式不对")
    private String realName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3578]\\d{9}$", message = "手机号格式不对")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Email
    private String email;

    private List<String> fileList;
}
