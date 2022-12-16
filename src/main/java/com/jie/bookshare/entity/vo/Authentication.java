package com.jie.bookshare.entity.vo;

import lombok.Data;

/**
 * @ClassName Authentication
 * @Description 后台登陆接收的用户密码类
 * @Author wuhaojie
 * @Date 2022/4/23 20:17
 */
@Data
public class Authentication {
    private String username;
    private String password;
}
