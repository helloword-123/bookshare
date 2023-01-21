package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.AdviceDTO;
import com.jie.bookshare.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-20
 */
@RestController
@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    private AdviceService adviceService;

    /**
     * 添加建议
     * @param adviceDTO
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody AdviceDTO adviceDTO){
        adviceService.add(adviceDTO);

        return Result.ok();
    }
}
