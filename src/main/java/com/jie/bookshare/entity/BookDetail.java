package com.jie.bookshare.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId
    private Integer id;

    private Integer categoryId;

    private String bookName;

    private String author;

    @ApiModelProperty(value = "图书出版时间")
    private Date publicationTime;

    private String pictiureUrl;

    private String isbn;

    private String description;

    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
