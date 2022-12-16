package com.jie.bookshare.service;

import com.jie.bookshare.entity.AclRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
public interface AclRoleService extends IService<AclRole> {
    boolean exists(String id);
}
