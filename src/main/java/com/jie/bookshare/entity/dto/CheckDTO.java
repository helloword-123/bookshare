package com.jie.bookshare.entity.dto;

import lombok.Data;

@Data
public class CheckDTO {

    private Integer id;

    private Integer checkerId;

    private String description;

    // 审核结果：0-不通过，1-通过
    private Integer status;
}
