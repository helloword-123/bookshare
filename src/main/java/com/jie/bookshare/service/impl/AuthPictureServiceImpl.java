package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.AuthPicture;
import com.jie.bookshare.mapper.AuthPictureMapper;
import com.jie.bookshare.service.AuthPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
@Service
public class AuthPictureServiceImpl extends ServiceImpl<AuthPictureMapper, AuthPicture> implements AuthPictureService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AuthPictureMapper authPictureMapper;

    /**
     * 根据authId查询所有认证图片
     *
     * @param authId
     * @return
     */
    //@Cacheable(value = "authImgList", key = "#authId")
    @Override
    public List<String> getAuthImgListByAuthId(Integer authId) {
        logger.info("Get auth's imageList By authId: {}.", authId);
        LambdaQueryWrapper<AuthPicture> con1 = new LambdaQueryWrapper<>();
        con1.eq(AuthPicture::getAuthId, authId);
        List<AuthPicture> authPictures = authPictureMapper.selectList(con1);
        List<String> imgList = new ArrayList<>();
        for (AuthPicture authPicture : authPictures) {
            imgList.add(authPicture.getPictureUrl());
        }
        logger.info("ImgList is: {}.", imgList);
        return imgList;
    }
}
