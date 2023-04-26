package com.jie.bookshare.controller;

import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.AuthenticationDTO;
import com.jie.bookshare.filter.ddos.AccessLimit;
import com.jie.bookshare.filter.repeatsubmit.RepeatSubmit;
import com.jie.bookshare.service.UserService;
import com.jie.bookshare.service.impl.OssService;
import com.jie.bookshare.utils.UploadCheckUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公共Controller
 *
 * @author wuhaojie
 * @since 2023-01-21
 */
@RestController
@RequestMapping("/common")
@Api(tags = "通用接口")
public class CommonController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private OssService ossService;

    @Resource
    private UserService userService;


    /**
     * 上传图片接口
     *
     * @param file 上传的图片文件
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation("添加时上传图片")
    @PostMapping("uploadFile")
    public Result uploadOssFile(
            @ApiParam("图片文件")
            @RequestParam("image")
            MultipartFile file) {
        log.info("图片上传");
        // 校验
        UploadCheckUtils.uploadVerify(file);
        //获取上传文件
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url", url);
    }

    /**
     * 测试：直接获取token
     *
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "测试：直接获取token")
    @GetMapping("test/getToken")
    public Result getToken() {
        String username = "admin";
        String password = "admin";
        List<String> info = userService.login(username, password); //info是{token,rId}
        if (info == null) {
            return Result.error().message("账号密码不匹配！");
        }
        return Result.ok().data(CommonConstant.TOKEN, info.get(0)).data("rId", info.get(1));
    }

    @RepeatSubmit(expireSeconds = 10, value = "111")
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @PostMapping("test")
    public String test(@RequestBody AuthenticationDTO auth){
        return auth.getUsername();
    }

}
