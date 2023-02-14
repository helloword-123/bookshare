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
 *  服务类
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
     * 根据token返回管理员信息
     *
     * @param token
     * @return AdminVo
     */
    // AdminVo getInfo(String token);

    boolean exists(String id);

    List<AclRole> findRoleForAdmin(Integer userId);

    Collection<AppGrantedAuthority> selectAuthoritiesForRoles(List<AclRole> roles);

    Boolean validatePassword(String aId, String password);

    /**
     * 微信授权登录后，根据登录信息返回顾客id
     *
     * @param
     * @return Integer
     */
    UserDTO getUserInfoByOpenid(String openid);

    /*
     * @Author Haojie
     * @Description 更新用户头像和昵称
     * @Param
     * @return
     **/
    void updateUserInfo(UserDTO userDTO);

    String saveUserAuthorityByUserId(Integer userId);

}
