package com.jie.bookshare.mapper;

import com.jie.bookshare.entity.CampusStaffAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
public interface CampusStaffAuthMapper extends BaseMapper<CampusStaffAuth> {

    /**
     * 获取最新的认证记录
     * @param userId
     * @return
     */
    CampusStaffAuth getNewestAuthByUserId(Integer userId);

    List<CampusStaffAuth> getAuthList();
}
