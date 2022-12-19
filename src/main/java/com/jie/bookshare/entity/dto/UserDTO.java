package com.jie.bookshare.entity.dto;

import lombok.Data;

import java.io.Serializable;

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
}
