package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.CampusStaffAuth;
import com.jie.bookshare.entity.dto.AuthDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
public interface CampusStaffAuthService extends IService<CampusStaffAuth> {

    /**
     * 添加校园认证记录
     * @param authDTO
     */
    void add(AuthDTO authDTO);

    /**
     * 根据userId获取用户的认证信息
     * @param userId
     */
    CampusStaffAuth getAuthInfo(Integer userId);
}
