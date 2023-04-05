package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.CampusStaffAuth;
import com.jie.bookshare.entity.dto.AuthDTO;
import com.jie.bookshare.entity.dto.CheckDTO;
import com.jie.bookshare.service.AuthPictureService;
import com.jie.bookshare.service.CampusStaffAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.util.List;

/**
 * 认证Controller
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
@Validated
@RestController
@RequestMapping("/campus-staff-auth")
public class CampusStaffAuthController {

    @Autowired
    private CampusStaffAuthService campusStaffAuthService;
    @Resource
    private AuthPictureService authPictureService;


    /**
     * 根据authId查询所有认证图片
     *
     * @param authId 认证记录id
     * @return
     */
    @ApiOperation(value = "根据authId查询所有认证图片")
    @PreAuthorize("hasAuthority('campus_staff_auth:query')")
    @GetMapping("/getAuthImgList/{authId}")
    public Result getAuthImgListByAuthId(@Digits(integer = 3, fraction = 0)
                                         @PathVariable Integer authId) {
        List<String> imgList = authPictureService.getAuthImgListByAuthId(authId);
        return Result.ok().data("imgList", imgList);
    }

    /**
     * 添加校园认证记录
     *
     * @param authDTO 前端添加校园认证记录传输的dto
     * @return
     */
    @ApiOperation(value = "添加校园认证记录")
    @PreAuthorize("hasAuthority('campus_staff_auth:add')")
    @PostMapping("/add")
    public Result add(@Valid
                      @RequestBody AuthDTO authDTO) {
        campusStaffAuthService.add(authDTO);
        return Result.ok();
    }

    /**
     * 根据userId获取用户的认证信息
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "根据userId获取用户的认证信息")
    @PreAuthorize("hasAuthority('campus_staff_auth:query')")
    @GetMapping("/getAuthInfo/{userId}")
    public Result getAuthInfo(@Digits(integer = 3, fraction = 0)
                              @PathVariable Integer userId) {
        CampusStaffAuth campusStaffAuth = campusStaffAuthService.getAuthInfo(userId);
        return Result.ok().data("campusStaffAuth", campusStaffAuth);
    }

    /**
     * 获取所有用户的认证记录（多次认证只返回最后一条）
     *
     * @return
     */
    @ApiOperation(value = "获取所有用户的认证记录（多次认证只返回最后一条）")
    @PreAuthorize("hasAuthority('campus_staff_auth:admin')")
    @GetMapping("/getAuthList")
    public Result getAuthList() {
        List<CampusStaffAuth> list = campusStaffAuthService.getAuthList();
        return Result.ok().data("list", list);
    }


    /**
     * 审核
     *
     * @param checkDTO 前端审核传输的dto
     * @return
     */
    @ApiOperation(value = "审核")
    @PreAuthorize("hasAuthority('campus_staff_auth:admin')")
    @PostMapping("/check")
    public Result checkAuth(@Valid
                            @RequestBody CheckDTO checkDTO) {
        campusStaffAuthService.checkAuth(checkDTO);
        return Result.ok();
    }
}
