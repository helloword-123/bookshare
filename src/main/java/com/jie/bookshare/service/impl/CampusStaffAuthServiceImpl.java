package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.AuthPicture;
import com.jie.bookshare.entity.CampusStaffAuth;
import com.jie.bookshare.entity.User;
import com.jie.bookshare.entity.dto.AuthDTO;
import com.jie.bookshare.entity.dto.CheckDTO;
import com.jie.bookshare.mapper.AuthPictureMapper;
import com.jie.bookshare.mapper.CampusStaffAuthMapper;
import com.jie.bookshare.mapper.UserMapper;
import com.jie.bookshare.mq.MQMessage;
import com.jie.bookshare.mq.MessageProducer;
import com.jie.bookshare.service.CampusStaffAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
@Service
public class CampusStaffAuthServiceImpl extends ServiceImpl<CampusStaffAuthMapper, CampusStaffAuth> implements CampusStaffAuthService {

    @Autowired
    private CampusStaffAuthMapper campusStaffAuthMapper;
    @Autowired
    private AuthPictureMapper authPictureMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageProducer messageProducer;

    /**
     * 添加校园认证记录
     *
     * @param authDTO
     */
    @Override
    public void add(AuthDTO authDTO) {
        CampusStaffAuth auth = new CampusStaffAuth();
        auth.setUserId(authDTO.getUserId());
        auth.setNumber(authDTO.getNumber());
        auth.setRealName(authDTO.getRealName());
        auth.setPhone(authDTO.getPhone());
        auth.setEmail(authDTO.getEmail());
        auth.setStatus(0);
        campusStaffAuthMapper.insert(auth);

        User user = new User();
        user.setId(authDTO.getUserId());
        user.setAuthId(auth.getId());
        userMapper.updateById(user);

        for (String url : authDTO.getFileList()) {
            AuthPicture authPicture = new AuthPicture();
            authPicture.setPictureUrl(url);
            authPicture.setAuthId(auth.getId());
            authPictureMapper.insert(authPicture);
        }
    }

    /**
     * 根据userId获取用户的认证信息
     *
     * @param userId
     */
    @Override
    public CampusStaffAuth getAuthInfo(Integer userId) {
        CampusStaffAuth auth = null;
        User user = userMapper.selectById(userId);
        if(user == null){
            return auth;
        }
        // 未认证
        if(user.getAuthId() == -1){
            return null;
        }
        // 返回最新的认证信息
        else{
            auth = campusStaffAuthMapper.getNewestAuthByUserId(userId);
        }
        return auth;
    }

    /**
     * 审核
     *
     * @param checkDTO
     * @return
     */
    @Override
    public void checkAuth(CheckDTO checkDTO) {
        CampusStaffAuth auth = campusStaffAuthMapper.selectById(checkDTO.getId());
        auth.setId(checkDTO.getId());
        auth.setCheckerId(checkDTO.getCheckerId());
        auth.setDescription(checkDTO.getDescription());
        auth.setCheckTime(new Date());
        auth.setStatus(checkDTO.getStatus());
        campusStaffAuthMapper.updateById(auth);


        // 推送消息到消息队列
        MQMessage mqMessage = new MQMessage(0, checkDTO.getCheckerId(), auth.getUserId(), new Date());
        mqMessage.addData("title", "认证审核结果");
        mqMessage.addData("message", (checkDTO.getStatus() == 1 ? "审核通过：" : "审核不通过：") + checkDTO.getDescription());
        messageProducer.lPush(mqMessage);

    }

    /**
     * 获取所有用户的认证记录（多次认证只返回最后一条）
     *
     * @return
     */
    @Override
    public List<CampusStaffAuth> getAuthList() {

        return campusStaffAuthMapper.getAuthList();
    }

    /**
     * 判断用户是否已认证
     *
     * @param id
     * @return
     */
    @Override
    public Boolean getUserIsAuth(Integer id) {

        User user = userMapper.selectById(id);
        if(user.getAuthId() == -1){
            return false;
        }

        LambdaQueryWrapper<CampusStaffAuth> con1 = new LambdaQueryWrapper<>();
        con1.eq(CampusStaffAuth::getUserId, id).eq(CampusStaffAuth::getStatus, 1);
        CampusStaffAuth auth = campusStaffAuthMapper.selectOne(con1);

        return auth != null;
    }
}
