package com.jie.bookshare.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookListWithCategoryDTO {

    private Integer cate_id;

    List<BookListDTO> list;
}
