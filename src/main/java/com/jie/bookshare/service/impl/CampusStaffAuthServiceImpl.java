package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.AuthPicture;
import com.jie.bookshare.entity.CampusStaffAuth;
import com.jie.bookshare.entity.User;
import com.jie.bookshare.entity.dto.AuthDTO;
import com.jie.bookshare.mapper.AuthPictureMapper;
import com.jie.bookshare.mapper.CampusStaffAuthMapper;
import com.jie.bookshare.mapper.UserMapper;
import com.jie.bookshare.service.CampusStaffAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
@Service
public class CampusStaffAuthServiceImpl extends ServiceImpl<CampusStaffAuthMapper, CampusStaffAuth> implements CampusStaffAuthService {

    @Autowired
    private CampusStaffAuthMapper campusStaffAuthMapper;
    @Autowired
    private AuthPictureMapper authPictureMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加校园认证记录
     *
     * @param authDTO
     */
    @Override
    public void add(AuthDTO authDTO) {
        CampusStaffAuth auth = new CampusStaffAuth();
        auth.setUserId(authDTO.getUserId());
        auth.setNumber(authDTO.getNumber());
        auth.setRealName(authDTO.getRealName());
        auth.setPhone(authDTO.getPhone());
        auth.setEmail(authDTO.getEmail());
        auth.setStatus(0);
        campusStaffAuthMapper.insert(auth);

        for (String url : authDTO.getFileList()) {
            AuthPicture authPicture = new AuthPicture();
            authPicture.setPictureUrl(url);
            authPicture.setAuthId(auth.getId());
            authPictureMapper.insert(authPicture);
        }
    }

    /**
     * 根据userId获取用户的认证信息
     *
     * @param userId
     */
    @Override
    public CampusStaffAuth getAuthInfo(Integer userId) {
        CampusStaffAuth auth = null;
        User user = userMapper.selectById(userId);
        // 未认证
        if(user.getAuthStatus() == -1){
            return null;
        }
        // 返回最新的认证信息
        else{
            auth = campusStaffAuthMapper.getNewestAuthByUserId(userId);
        }
        return auth;
    }
}
