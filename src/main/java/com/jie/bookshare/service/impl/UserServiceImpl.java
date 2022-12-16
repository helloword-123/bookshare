package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jie.bookshare.entity.*;
import com.jie.bookshare.entity.security.AppGrantedAuthority;
import com.jie.bookshare.entity.security.AppUserDetails;
import com.jie.bookshare.entity.security.UserType;
import com.jie.bookshare.entity.vo.UserVo;
import com.jie.bookshare.mapper.*;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.utils.JsonUtil;
import com.jie.bookshare.utils.JwtUtil;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.acl.Acl;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        //查找用户名和密码，并比对密码
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getAccount, username);
        User user = baseMapper.selectOne(cond);
        if (user == null)
            return null;
        if (!passwordEncoder.matches(password, user.getPassword()))
            return null;
        //查询权限
        List<AclRole> roles = findRoleForAdmin(user.getId());
        UserType userType = UserType.ADMIN;
        for (AclRole role : roles) {
            if ("super_admin".equals(role.getKey())) {
                userType = UserType.SUPER_ADMIN;
                break;
            }
        }
        Collection<AppGrantedAuthority> authorities = selectAuthoritiesForRoles(roles);
        //把登录状态保存到Redis中
        //登录状态保存为hash类型，里面保存token和用户信息
        AppUserDetails userDetails = new AppUserDetails(
                String.valueOf(user.getId()), null, authorities, userType);
        String token = JwtUtil.generateToken(String.valueOf(user.getId()));
        String key = IRedisService.concatKey("user_details", String.valueOf(user.getId()));
        Map<String, String> hash = new HashMap<>();
        hash.put("token", token);
        hash.put("user_details", JsonUtil.toJson(userDetails));
        redisService.putAll(key, hash);
        redisService.expire(key, 1, TimeUnit.DAYS);
        return Arrays.asList(token, user.getId().toString());
    }

    @Override
    public Collection<AppGrantedAuthority> selectAuthoritiesForRoles(List<AclRole> roles) {
        List<Integer> roleIdList = roles
                .stream().map(AclRole::getId).collect(Collectors.toList());
        QueryWrapper<AclRolePermission> cond2 = new QueryWrapper<>();
        cond2.in("role_id", roleIdList).select("distinct menu_id");
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
        if(admin==null){
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
    public Integer getUserIdByLoginInfo(UserVo userVo) {
        Integer cId = null;
        //先根据用户名判断该用户存不存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //转换，名字可能包含emoji表情
        String nameConvert = EmojiParser.parseToAliases(userVo.getCName());
        wrapper.eq("c_name",nameConvert);
        User customer = baseMapper.selectOne(wrapper);  //这里默认用户昵称不一样
        if(customer==null){
            User customer1 = new User();
            customer1.setUserName(nameConvert);
            customer1.setAvatarUrl(userVo.getCAvatarUrl());
            baseMapper.insert(customer1);
            cId = baseMapper.selectOne(wrapper).getId();
        }else {
            cId = customer.getId();
        }
        return cId;
    }
}
