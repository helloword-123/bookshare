package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.AdviceDTO;
import com.jie.bookshare.service.AdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 评价Controller
 *
 * @author wuhaojie
 * @since 2023-01-20
 */
@Validated
@RestController
@RequestMapping("/advice")
@Api(tags = "评价相关")
public class AdviceController {

    @Autowired
    private AdviceService adviceService;

    /**
     * 添加建议
     *
     * @param adviceDTO 前端提交的评价dto
     * @return
     */
    @ApiOperation(value = "添加建议")
    @PreAuthorize("hasAuthority('advice:add')")
    @PostMapping("/add")
    public Result add(
            @ApiParam(value = "评价信息")
            @Valid
            @RequestBody AdviceDTO adviceDTO) {
        adviceService.add(adviceDTO);
        return Result.ok();
    }
}
