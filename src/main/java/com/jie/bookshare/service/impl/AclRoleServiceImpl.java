package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jie.bookshare.entity.AclRole;
import com.jie.bookshare.mapper.AclRoleMapper;
import com.jie.bookshare.service.AclRoleService;
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
public class AclRoleServiceImpl extends ServiceImpl<AclRoleMapper, AclRole> implements AclRoleService {
    /**
     * 检查ID对应的角色是否存在
     */
    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<AclRole> cond = new LambdaQueryWrapper<>();
        cond.eq(AclRole::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }
}
