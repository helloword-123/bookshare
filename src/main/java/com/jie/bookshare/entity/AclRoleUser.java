package com.jie.bookshare.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AclRoleUser对象", description="")
public class AclRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;

    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
