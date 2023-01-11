package com.jie.bookshare.entity.dto;

import com.jie.bookshare.entity.BookCategory;
import lombok.Data;

import java.util.List;

@Data
public class BookListWithCategoryDTO {

    private BookCategory bookCategory;

    private List<BookListDTO> list;
}
