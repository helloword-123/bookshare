package com.jie.bookshare.entity.dto;

import lombok.Data;

@Data
public class BookListDTO {

    private Integer bookId;

    private String name;

    private String author;

    private String picture_url;

    private Integer sharerId;

    private String sharer;

    private String location;
}
