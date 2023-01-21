package com.jie.bookshare.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdviceDTO {
    private Integer userId;

    private String content;

    private String contact;

    private List<String> fileList;

    private Double star;
}
