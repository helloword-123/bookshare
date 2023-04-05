package com.jie.bookshare.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckDTO {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotNull(message = "审核员id不能为空")
    private Integer checkerId;

    private String description;

    // 审核结果：0-不通过，1-通过
    @NotNull(message = "审核结果不能为空")
    private Integer status;
}
