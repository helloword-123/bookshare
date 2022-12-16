package com.jie.bookshare.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="Book对象", description="")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "外键")
    private Integer bookDetailId;

    @ApiModelProperty(value = "漂流状态")
    private String status;

    @ApiModelProperty(value = "图书当前地址")
    private String currentAddress;

    @ApiModelProperty(value = "图书分享发布时间")
    private LocalDate releaseTime;

    @ApiModelProperty(value = "外键：当前共享发布者id")
    private Integer sharerId;

    private Integer publisherId;

    @ApiModelProperty(value = "图书描述")
    private String description;

    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
