package com.jie.bookshare.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthDTO {

    private Integer userId;
    private String number;
    private String realName;
    private String phone;
    private String email;
    private List<String> fileList;
}
