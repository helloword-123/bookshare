package com.jie.bookshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="BookDrift对象", description="")
public class BookDrift implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String sharerId;

    private String userId;

    private String driftAddress;

    @ApiModelProperty(value = "使用者共享图书的时间点")
    private LocalDate driftTime;

    @ApiModelProperty(value = "外键：图书的id")
    private Integer bookId;

    @ApiModelProperty(value = "读书心得、分享愿望")
    private String note;


}
