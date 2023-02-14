package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jie.bookshare.entity.AuthPicture;
import com.jie.bookshare.mapper.AuthPictureMapper;
import com.jie.bookshare.service.AuthPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Resource
    private AuthPictureMapper authPictureMapper;

    /**
     * 根据authId查询所有认证图片
     *
     * @param authId
     * @return
     */
    @Override
    public List<String> getAuthImgListByAuthId(Integer authId) {
        LambdaQueryWrapper<AuthPicture> con1 = new LambdaQueryWrapper<>();
        con1.eq(AuthPicture::getAuthId, authId);
        List<AuthPicture> authPictures = authPictureMapper.selectList(con1);
        List<String> imgList = new ArrayList<>();
        for (AuthPicture authPicture : authPictures) {
            imgList.add(authPicture.getPictureUrl());
        }
        return imgList;
    }
}
