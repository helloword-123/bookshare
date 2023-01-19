package com.jie.bookshare.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookListDTO {

    private Integer bookId;

    private String name;

    private String author;

    private String picture_url;

    private Integer sharerId;

    private String sharer;

    private String location;

    private String note;

    private Double latitude;

    private Double longitude;

    private Double distance;

    private Date releaseTime;
}
