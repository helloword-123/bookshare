package com.jie.bookshare.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CampusStaffAuth对象", description="")
public class CampusStaffAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String realName;

    private String phone;

    private String email;

    private String number;

    private Integer userId;

    @ApiModelProperty(value = "状态，0-未审核，1-审核通过，2-审核未通过")
    private Integer status;

    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "审核人id")
    private Integer checkerId;

    private Date checkTime;

    @ApiModelProperty(value = "审核人回复信息")
    private String description;
}
