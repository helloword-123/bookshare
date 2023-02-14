package com.jie.bookshare.service;

import com.jie.bookshare.entity.AuthPicture;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
public interface AuthPictureService extends IService<AuthPicture> {

    /**
     * 根据authId查询所有认证图片
     * @param authId
     * @return
     */
    List<String> getAuthImgListByAuthId(Integer authId);
}
