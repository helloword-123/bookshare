package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.CampusStaffAuth;
import com.jie.bookshare.entity.dto.AuthDTO;
import com.jie.bookshare.entity.dto.CheckDTO;

import java.util.List;

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

    /**
     * 审核
     * @param checkDTO
     * @return
     */
    void checkAuth(CheckDTO checkDTO);

    /**
     * 获取所有用户的认证记录（多次认证只返回最后一条）
     * @return
     */
    List<CampusStaffAuth> getAuthList();

    /**
     * 判断用户是否已认证
     * @param id
     * @return
     */
    Boolean getUserIsAuth(Integer id);
}
