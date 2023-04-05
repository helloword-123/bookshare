package com.jie.bookshare.controller;


import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.CampusStaffAuth;
import com.jie.bookshare.entity.dto.AuthDTO;
import com.jie.bookshare.entity.dto.CheckDTO;
import com.jie.bookshare.filter.ddos.AccessLimit;
import com.jie.bookshare.service.AuthPictureService;
import com.jie.bookshare.service.CampusStaffAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "认证相关")
public class CampusStaffAuthController {

    @Resource
    private CampusStaffAuthService campusStaffAuthService;
    @Resource
    private AuthPictureService authPictureService;


    /**
     * 根据authId查询所有认证图片
     *
     * @param authId 认证记录id
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "根据authId查询所有认证图片")
    @PreAuthorize("hasAuthority('campus_staff_auth:query')")
    @GetMapping("/getAuthImgList/{authId}")
    public Result getAuthImgListByAuthId(
            @ApiParam(value = "认证id", example = "0")
            @Digits(integer = 3, fraction = 0)
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
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "添加校园认证记录")
    @PreAuthorize("hasAuthority('campus_staff_auth:add')")
    @PostMapping("/add")
    public Result add(
            @ApiParam(value = "认证信息")
            @Valid
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
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "根据userId获取用户的认证信息")
    @PreAuthorize("hasAuthority('campus_staff_auth:query')")
    @GetMapping("/getAuthInfo/{userId}")
    public Result getAuthInfo(
            @ApiParam(value = "用户id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer userId) {
        CampusStaffAuth campusStaffAuth = campusStaffAuthService.getAuthInfo(userId);
        return Result.ok().data("campusStaffAuth", campusStaffAuth);
    }

    /**
     * 获取所有用户的认证记录（多次认证只返回最后一条）
     *
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
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
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "审核")
    @PreAuthorize("hasAuthority('campus_staff_auth:admin')")
    @PostMapping("/check")
    public Result checkAuth(@Valid
                            @RequestBody CheckDTO checkDTO) {
        campusStaffAuthService.checkAuth(checkDTO);
        return Result.ok();
    }
}
