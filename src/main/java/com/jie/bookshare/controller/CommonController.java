package com.jie.bookshare.controller;

import com.jie.bookshare.common.Result;
import com.jie.bookshare.service.UserService;
import com.jie.bookshare.service.impl.OssService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName CommonController
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/4/17 9:58
 */

@RestController
@RequestMapping("/common")
public class CommonController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OssService ossService;

    @Autowired
    private UserService userService;


    @ApiOperation("添加时上传图片")
    @PostMapping("uploadFile")
    public Result uploadOssFile(
            @ApiParam("图片文件")
            @RequestParam("image")
                    MultipartFile file) {
        log.info("图片上传");
        //获取上传文件
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url", url);
    }

    @ApiOperation(value = "测试：直接获取token")
    @GetMapping("test/getToken")
    public Result getToken() {
        String username = "admin";
        String password = "admin";
        List<String> info = userService.login(username, password); //info是{token,rId}
        if (info == null) {
            return Result.error().message("账号密码不匹配！");
        }
        return Result.ok().data("token", info.get(0)).data("rId",info.get(1));
    }

}
