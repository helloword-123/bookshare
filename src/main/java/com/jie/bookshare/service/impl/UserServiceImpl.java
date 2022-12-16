package com.jie.bookshare.service.impl;

import com.jie.bookshare.entity.User;
import com.jie.bookshare.mapper.UserMapper;
import com.jie.bookshare.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
