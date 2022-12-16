package com.jie.bookshare.entity;

import java.time.LocalDate;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BookDetail对象", description="")
public class BookDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer categoryId;

    private String bookName;

    private String author;

    @ApiModelProperty(value = "图书出版时间")
    private LocalDate publicationTime;

    private String pictiureUrl;

    private String isbn;

    private String description;


}
