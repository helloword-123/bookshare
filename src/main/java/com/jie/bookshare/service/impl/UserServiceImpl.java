package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.*;
import com.jie.bookshare.entity.dto.UserDTO;
import com.jie.bookshare.entity.security.AppGrantedAuthority;
import com.jie.bookshare.entity.security.AppUserDetails;
import com.jie.bookshare.entity.security.UserType;
import com.jie.bookshare.mapper.*;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.service.UserService;
import com.jie.bookshare.utils.JsonUtil;
import com.jie.bookshare.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private AclRoleUserMapper aclRoleUserMapper;
    @Autowired
    private AclRoleMapper aclRoleMapper;
    @Autowired
    private AclRolePermissionMapper aclRolePermissionMapper;
    @Autowired
    private AclPermissionTypeMapper aclPermissionTypeMapper;
    @Autowired
    private AclPermissionMapper aclPermissionMapper;

    @Override
    public List<String> login(String username, String password) {
        logger.info("login: username:{}, password:{}", username, password);

        // 查找用户名和密码，并比对密码
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getAccount, username);
        User user = baseMapper.selectOne(cond);
        if (user == null)
            return null;
        if (!passwordEncoder.matches(password, user.getPassword()))
            return null;

        String token = saveUserAuthorityByUserId(user.getId());

        return Arrays.asList(token, user.getId().toString());
    }

    /*
     * @Author Haojie
     * @Description 根据用户id生成token返回，并且查询该用户的权限信息且保存到redis
     * @Param
     * @return
     **/
    @Override
    public String saveUserAuthorityByUserId(Integer userId){
        String token = null;

        //查询权限
        List<AclRole> roles = findRoleForAdmin(userId);
        UserType userType = UserType.USER;
        for (AclRole role : roles) {
            if ("super_admin".equals(role.getKey())) {
                userType = UserType.SUPER_ADMIN;
                break;
            } else if ("admin".equals(role.getKey())) {
                userType = UserType.ADMIN;
                break;
            }
        }
        Collection<AppGrantedAuthority> authorities = selectAuthoritiesForRoles(roles);
        //把登录状态保存到Redis中
        //登录状态保存为hash类型，里面保存token和用户信息
        AppUserDetails userDetails = new AppUserDetails(
                String.valueOf(userId), null, authorities, userType);
        token = JwtUtil.generateToken(String.valueOf(userId));
        String key = IRedisService.concatKey("user_details", String.valueOf(userId));
        Map<String, String> hash = new HashMap<>();
        hash.put("token", token);
        hash.put("user_details", JsonUtil.toJson(userDetails));
        redisService.putAll(key, hash);
        redisService.expire(key, 1, TimeUnit.DAYS);

        return token;
    }

    @Override
    public Collection<AppGrantedAuthority> selectAuthoritiesForRoles(List<AclRole> roles) {
        List<Integer> roleIdList = roles
                .stream().map(AclRole::getId).collect(Collectors.toList());
        QueryWrapper<AclRolePermission> cond2 = new QueryWrapper<>();
        cond2.in("role_id", roleIdList).select("distinct permission_id");
        List<AclRolePermission> menuRoles = aclRolePermissionMapper.selectList(cond2);

        LambdaQueryWrapper<AclPermission> cond3 = new LambdaQueryWrapper<>();
        List<Integer> menuIdList = menuRoles
                .stream().map(AclRolePermission::getPermissionId).collect(Collectors.toList());
        if (menuIdList.isEmpty()) return Collections.emptyList();
        cond3.in(AclPermission::getId, menuIdList)
                .select(AclPermission::getKey, AclPermission::getTypeId);
        List<AclPermission> menus = aclPermissionMapper.selectList(cond3);

        List<AppGrantedAuthority> authorities = new ArrayList<>();
        for (AclPermission menu : menus) {
            LambdaQueryWrapper<AclPermissionType> cond4 = new LambdaQueryWrapper<>();
            cond4.eq(AclPermissionType::getId, menu.getTypeId()).select(AclPermissionType::getKey);
            AclPermissionType menuType = aclPermissionTypeMapper.selectOne(cond4);
            AppGrantedAuthority authority =
                    new AppGrantedAuthority(menuType.getKey() + ":" + menu.getKey());
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public Boolean validatePassword(String aId, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, aId);
        User admin = baseMapper.selectOne(wrapper);
        if (admin == null) {
            return false;
        }
        if (!passwordEncoder.matches(password, admin.getPassword()))
            return false;
        return true;
    }

    /**
     * 查询管理员所具有的所有角色
     */
    @Override
    public List<AclRole> findRoleForAdmin(Integer userId) {
        LambdaQueryWrapper<AclRoleUser> cond1 = new LambdaQueryWrapper<>();
        cond1.eq(AclRoleUser::getUserId, userId).select(AclRoleUser::getRoleId);
        List<AclRoleUser> userRoles = aclRoleUserMapper.selectList(cond1);
        List<Integer> roleIdList = userRoles
                .stream().map(AclRoleUser::getRoleId).collect(Collectors.toList());
        LambdaQueryWrapper<AclRole> cond2 = new LambdaQueryWrapper<>();
        cond2.in(AclRole::getId, roleIdList).select(AclRole::getKey, AclRole::getId);
        return aclRoleMapper.selectList(cond2);
    }

    /**
     * 检测ID对应的管理员是否存在
     */
    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }

    @Override
    public UserDTO getUserInfoByOpenid(String openid) {
        UserDTO userDTO = new UserDTO();
        //先根据用户名判断该用户存不存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        User user1 = baseMapper.selectOne(wrapper);
        if(user1 == null) {
            // openid不存在时新建用户
            User user2 = new User();
            user2.setOpenid(openid);
            baseMapper.insert(user2);
            userDTO.setId(user2.getId());

            logger.info("getUserInfoByOpenid：this openid doesn't match any user in database. Create a new user with this openid");
        } else {
            userDTO.setId(user1.getId());
            userDTO.setNickName(user1.getUserName());
            userDTO.setAvatarUrl(user1.getAvatarUrl());
            userDTO.setPhone(user1.getPhone());

            logger.info("getUserInfoByOpenid：query userid:{}, username:{}, avatarurl:{}", user1.getId(),user1.getUserName(),user1.getAvatarUrl());
        }



        return userDTO;

    }

    @Override
    public void updateAvatarAndName(UserDTO userDTO) {
        logger.info("updateAvatarAndName： id:{}, userName:{}, avatarUrl:{}", userDTO.getId(), userDTO.getNickName(), userDTO.getAvatarUrl());
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getNickName());
        user.setAvatarUrl(userDTO.getAvatarUrl());
        baseMapper.updateById(user);
    }
}
