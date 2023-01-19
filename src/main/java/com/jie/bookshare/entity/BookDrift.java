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
 * @since 2022-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BookDrift对象", description="")
public class BookDrift implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer sharerId;

    @ApiModelProperty(value = "分享者填写的昵称")
    private String sharerName;

    @ApiModelProperty(value = "分享者填写的手机号")
    private String sharerPhone;

    private Integer borrowerId;

    @ApiModelProperty(value = "图书分享发布位置")
    private String driftAddress;

    @ApiModelProperty(value = "纬度")
    private Double latitude;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "图书分享发布时间")
    private Date releaseTime;

    @ApiModelProperty(value = "使用者共享图书的时间点")
    private Date driftTime;

    @ApiModelProperty(value = "外键：图书的id")
    private Integer bookId;

    @ApiModelProperty(value = "读书心得、分享愿望")
    private String note;

    @ApiModelProperty(value = "漂流状态：0-审核中，1-审核成功，已发布，2-审核失败，3-共享中，4-此次漂流结束，5-此书漂流结束")
    private Integer status;

    @ApiModelProperty(value = "漂流次数，即代表这本书此次在第几次漂流中")
    private Integer driftNum;

    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
