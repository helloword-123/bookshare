package com.jie.bookshare.entity.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookListDTO {

    /**
     * 图书信息
     */
    private Integer bookId;
    private String name;
    private String author;
    private String picture_url;
    private Integer categoryId;
    private String detail;
    private String publishingHouse;
    private String publishingTime;
    private String isbn;


    /**
     * 图书漂流信息
     */
    private Integer driftId;
    private Integer sharerId;
    private String sharer;
    private String sharerPhone;
    private String location;
    private String note;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private Date releaseTime;
    private Integer status;
    private Integer borrowerId;
    private String borrowerName;
    private Date driftTime;
    private Integer driftNum;

    private List<String> imgList;
}
