package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.CampusStaffAuth;
import com.jie.bookshare.entity.dto.AuthDTO;
import com.jie.bookshare.entity.dto.CheckDTO;
import com.jie.bookshare.service.CampusStaffAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
@RestController
@RequestMapping("/campus-staff-auth")
public class CampusStaffAuthController {

    @Autowired
    private CampusStaffAuthService campusStaffAuthService;

    /**
     * 添加校园认证记录
     * @param authDTO
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody AuthDTO authDTO){
        campusStaffAuthService.add(authDTO);

        return Result.ok();
    }

    /**
     * 根据userId获取用户的认证信息
     * @param userId
     * @return
     */
    @GetMapping("/getAuthInfo/{userId}")
    public Result getAuthInfo(@PathVariable Integer userId){
        CampusStaffAuth campusStaffAuth = campusStaffAuthService.getAuthInfo(userId);

        return Result.ok().data("campusStaffAuth", campusStaffAuth);
    }

    /**
     * 获取所有用户的认证记录
     * @return
     */
    @GetMapping("/getAuthList")
    public Result getAuthList(){
        List<CampusStaffAuth> list = campusStaffAuthService.list(null);

        return Result.ok().data("list", list);
    }


    /**
     * 审核
     * @param checkDTO
     * @return
     */
    @PostMapping("/check")
    public Result checkAuth(@RequestBody CheckDTO checkDTO){
        campusStaffAuthService.checkAuth(checkDTO);

        return Result.ok();
    }
}
