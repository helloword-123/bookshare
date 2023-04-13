package com.jie.bookshare.service;

import com.jie.bookshare.entity.AclRole;
import com.jie.bookshare.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.security.AppGrantedAuthority;
import com.jie.bookshare.entity.dto.UserDTO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
public interface UserService extends IService<User> {

    /**
     * 用户名密码验证并返回token
     *
     * @param username, password
     * @return token和rId组成的列表
     */
    List<String> login(String username, String password);

    /**
     * 判断该id的用户是否存在
     *
     * @param id 用户id
     */
    boolean exists(String id);

    /**
     * 查询userId的所有角色
     *
     * @param userId 用户id
     */
    List<AclRole> findRoleForAdmin(Integer userId);

    /**
     * 查询角色的所有权限
     *
     * @param roles 所有角色
     */
    Collection<AppGrantedAuthority> selectAuthoritiesForRoles(List<AclRole> roles);

    /**
     * 查询userId的所有权限并保存到Redis
     *
     * @param userId 用户id
     */
    String saveUserAuthorityByUserId(Integer userId);

    /**
     * 验证密码是否正确
     *
     * @param aId      用户id
     * @param password 密码
     */
    Boolean validatePassword(String aId, String password);

    /**
     * 微信授权登录后，根据登录信息返回顾客id
     *
     * @param openid openid
     */
    UserDTO getUserInfoByOpenid(String openid);

    /**
     * 更新用户头像和昵称
     *
     * @param userDTO 用户信息dto
     */
    void updateUserInfo(UserDTO userDTO);

    UserDTO getUserInfoByUserId(String userId);
}
