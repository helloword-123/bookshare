package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.RedisKeys;
import com.jie.bookshare.entity.*;
import com.jie.bookshare.entity.dto.UserDTO;
import com.jie.bookshare.entity.security.AppGrantedAuthority;
import com.jie.bookshare.entity.security.AppUserDetails;
import com.jie.bookshare.entity.security.UserType;
import com.jie.bookshare.exception.CustomizeRuntimeException;
import com.jie.bookshare.mapper.*;
import com.jie.bookshare.service.CampusStaffAuthService;
import com.jie.bookshare.service.IRedisService;
import com.jie.bookshare.service.UserService;
import com.jie.bookshare.utils.JsonUtil;
import com.jie.bookshare.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IRedisService redisService;
    @Resource
    private AclRoleUserMapper aclRoleUserMapper;
    @Resource
    private AclRoleMapper aclRoleMapper;
    @Resource
    private AclRolePermissionMapper aclRolePermissionMapper;
    @Resource
    private AclPermissionTypeMapper aclPermissionTypeMapper;
    @Resource
    private AclPermissionMapper aclPermissionMapper;
    @Resource
    private CampusStaffAuthService campusStaffAuthService;


    @Override
    public List<String> login(String username, String password) {
        logger.info("Username:{}, password:{}", username, password);

        // 查找用户名和密码，并比对密码
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getAccount, username);
        User user = baseMapper.selectOne(cond);
        if (user == null) {
            logger.info("User does not exist!");
            return null;
        }
        if (!passwordEncoder.matches(password, user.getPassword())){
            logger.info("Password does not match!");
            return null;
        }

        String token = saveUserAuthorityByUserId(user.getId());

        return Arrays.asList(token, user.getId().toString());
    }

    @Override
    public String saveUserAuthorityByUserId(Integer userId){
        logger.info("Save user's authority by userId: {}.", userId);
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
        String key = IRedisService.concatKey(RedisKeys.USER_INFO, String.valueOf(userId));
        Map<String, String> hash = new HashMap<>();
        hash.put(CommonConstant.TOKEN, token);
        hash.put(RedisKeys.USER_INFO, JsonUtil.toJson(userDetails));
        redisService.putAll(key, hash);
        redisService.expire(key, 1, TimeUnit.DAYS);
        logger.info("Token is: {}, user_details is {}.", token, userDetails);
        return token;
    }

    @Override
    public Collection<AppGrantedAuthority> selectAuthoritiesForRoles(List<AclRole> roles) {
        logger.info("Select authorities for roles: {}.", roles);
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
        logger.info("Authrities is: {}.", authorities);
        return authorities;
    }

    @Override
    public Boolean validatePassword(String aId, String password) {
        logger.info("Validate the password: {}, adminId is: {}.", password, aId);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, aId);
        User admin = baseMapper.selectOne(wrapper);
        if (admin == null) {
            logger.info("adminId: {} does not exist!", aId);
            return false;
        }
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            logger.info("Password does not match!");
            return false;
        }
        return true;
    }

    @Override
    public List<AclRole> findRoleForAdmin(Integer userId) {
        logger.info("Find roles for admin, adminId is: {}.", userId);
        LambdaQueryWrapper<AclRoleUser> cond1 = new LambdaQueryWrapper<>();
        cond1.eq(AclRoleUser::getUserId, userId).select(AclRoleUser::getRoleId);
        List<AclRoleUser> userRoles = aclRoleUserMapper.selectList(cond1);
        List<Integer> roleIdList = userRoles
                .stream().map(AclRoleUser::getRoleId).collect(Collectors.toList());
        LambdaQueryWrapper<AclRole> cond2 = new LambdaQueryWrapper<>();
        cond2.in(AclRole::getId, roleIdList).select(AclRole::getKey, AclRole::getId);
        List<AclRole> aclRoles = aclRoleMapper.selectList(cond2);

        logger.info("Roles is {}.", aclRoles);
        return aclRoles;
    }

    @Override
    public boolean exists(String id) {
        logger.info("judge id: {} if exists.", id);
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }

    //@CachePut(value = "userInfo#24*3600", key = "#openid")
    @Override
    public UserDTO getUserInfoByOpenid(String openid) {
        logger.info("Get userInfo by openid: {}.", openid);
        UserDTO userDTO = new UserDTO();
        //先根据用户名判断该用户存不存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        User user1 = baseMapper.selectOne(wrapper);
        if(user1 == null) {
            // openid不存在时新建用户
            User user2 = new User();
            user2.setOpenid(openid);
            user2.setTotalShareNum(0);
            user2.setTotalUseNum(0);
            user2.setAuthId(-1);
            user2.setUserName("快乐小青蛙");
            user2.setAvatarUrl("https://edu-wuhaojie.oss-cn-shenzhen.aliyuncs.com/bookshare/2023/01/01/14557b6d05f940949138367ebee89090f8S9a6ph8Rz9d997e5b2fa33d4fe0296c25fb672d07e.jpeg");
            baseMapper.insert(user2);
            userDTO.setId(user2.getId());

            // 添加user角色
            LambdaQueryWrapper<AclRole> con1 = new LambdaQueryWrapper<>();
            con1.eq(AclRole::getKey, "user");
            AclRole aclRole = aclRoleMapper.selectOne(con1);
            AclRoleUser aclRoleUser = new AclRoleUser();
            aclRoleUser.setUserId(user2.getId());
            aclRoleUser.setRoleId(aclRole.getId());
            aclRoleUserMapper.insert(aclRoleUser);

            logger.info("This openid doesn't match any user in database. Create a new user with this openid");
        } else {
            userDTO.setId(user1.getId());
            userDTO.setNickName(user1.getUserName());
            userDTO.setAvatarUrl(user1.getAvatarUrl());
            userDTO.setPhone(user1.getPhone());

            userDTO.setAuthId(user1.getAuthId());
            userDTO.setIsAuth(campusStaffAuthService.getUserIsAuth(user1.getId()));

            List<AclRole> roleForAdmin = this.findRoleForAdmin(user1.getId());
            List<String> roles = new ArrayList<>();
            for (AclRole aclRole : roleForAdmin) {
                roles.add(aclRole.getKey());
            }
            userDTO.setRoles(roles);

            logger.info("Query userid:{}, username:{}, avatarurl:{}", user1.getId(),user1.getUserName(),user1.getAvatarUrl());
        }

        return userDTO;

    }

    @Override
    //@CachePut(value = "userInfo#24*3600", key = "#userDTO.id")
    public void updateUserInfo(UserDTO userDTO) {
        logger.info("id:{}, userName:{}, avatarUrl:{}", userDTO.getId(), userDTO.getNickName(), userDTO.getAvatarUrl());
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getNickName());
        user.setAvatarUrl(userDTO.getAvatarUrl());
        user.setPhone(userDTO.getPhone());
        baseMapper.updateById(user);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public UserDTO getUserInfoByUserId(String userId) {
        if(userId == null){
            throw new CustomizeRuntimeException("用户id不能为空！");
        }
        //先根据用户名判断该用户存不存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userId);
        User user1 = baseMapper.selectOne(wrapper);
        if(user1 == null) {
            throw new CustomizeRuntimeException("用户不存在！");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user1.getId());
        userDTO.setNickName(user1.getUserName());
        userDTO.setAvatarUrl(user1.getAvatarUrl());
        userDTO.setPhone(user1.getPhone());
        userDTO.setAuthId(user1.getAuthId());
        userDTO.setIsAuth(campusStaffAuthService.getUserIsAuth(user1.getId()));

        List<AclRole> roleForAdmin = this.findRoleForAdmin(user1.getId());
        List<String> roles = new ArrayList<>();
        for (AclRole aclRole : roleForAdmin) {
            roles.add(aclRole.getKey());
        }
        userDTO.setRoles(roles);

        return userDTO;
    }
}
