package com.jie.bookshare.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AdviceDTO {
    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotBlank(message = "评价内容不能为空")
    private String content;

    @NotBlank(message = "联系方式不能为空")
    private String contact;

    private List<String> fileList;

    @NotNull(message = "评价星级不能为空")
    private Double star;
}
