package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.service.AuthPictureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/auth-picture")
public class AuthPictureController {

    @Resource
    private AuthPictureService authPictureService;

    /**
     * 根据authId查询所有认证图片
     * @param authId
     * @return
     */
    @GetMapping("/getAuthImgList/{authId}")
    public Result getAuthImgListByAuthId(@PathVariable Integer authId){
        List<String> imgList = authPictureService.getAuthImgListByAuthId(authId);

        return Result.ok().data("imgList", imgList);
    }
}
