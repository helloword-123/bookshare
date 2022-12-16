package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jie.bookshare.entity.AclPermission;
import com.jie.bookshare.mapper.AclPermissionMapper;
import com.jie.bookshare.service.AclPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Service
public class AclPermissionServiceImpl extends ServiceImpl<AclPermissionMapper, AclPermission> implements AclPermissionService {
    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<AclPermission> cond = new LambdaQueryWrapper<>();
        cond.eq(AclPermission::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }
}
