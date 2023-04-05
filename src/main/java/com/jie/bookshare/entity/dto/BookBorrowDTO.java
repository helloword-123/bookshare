package com.jie.bookshare.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookBorrowDTO {
    @NotNull(message = "借阅者id不能为空")
    private Integer borrowId;

    @NotNull(message = "图书漂流id不能为空")
    private Integer driftId;
}
