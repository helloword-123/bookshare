package com.jie.bookshare.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CustomerVo
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/3/28 17:01
 */
@Data
public class UserDTO implements Serializable {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^[\\w\\u4e00-\\u9fa5]{5,18}$", message = "昵称格式不对")
    private String nickName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3578]\\d{9}$", message = "手机号格式不对")
    private String phone;

    private String avatarUrl;
    @ApiModelProperty(value = "认证id，-1-未审核")
    private Integer authId;
    private List<String> roles;
    // 是否认证通过
    private Boolean isAuth;
}
