package com.jie.bookshare.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private Integer id;
    private String nickName;
    private String avatarUrl;
    private String phone;
    @ApiModelProperty(value = "认证id，-1-未审核")
    private Integer authId;
    private List<String> roles;

    // 是否认证通过
    private Boolean isAuth;
}
