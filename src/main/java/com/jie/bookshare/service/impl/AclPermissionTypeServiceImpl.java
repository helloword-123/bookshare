package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jie.bookshare.entity.AclPermissionType;
import com.jie.bookshare.mapper.AclPermissionTypeMapper;
import com.jie.bookshare.service.AclPermissionTypeService;
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
public class AclPermissionTypeServiceImpl extends ServiceImpl<AclPermissionTypeMapper, AclPermissionType> implements AclPermissionTypeService {
    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<AclPermissionType> cond = new LambdaQueryWrapper<>();
        cond.eq(AclPermissionType::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }
}
