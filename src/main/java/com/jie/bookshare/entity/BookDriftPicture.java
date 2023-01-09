package com.jie.bookshare.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BookDriftPicture对象", description="")
public class BookDriftPicture implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer driftId;

    private Integer pictureId;


}
