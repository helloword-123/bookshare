package com.jie.bookshare.controller;


import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.filter.ddos.AccessLimit;
import com.jie.bookshare.filter.repeatsubmit.RepeatSubmit;
import com.jie.bookshare.service.BookCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;
import java.util.List;

/**
 * 图书收藏Controller
 *
 * @author wuhaojie
 * @since 2023-02-14
 */
@Validated
@RestController
@RequestMapping("/book-collect")
@Api(tags = "图书收藏相关")
public class BookCollectController {

    @Resource
    private BookCollectService bookCollectService;

    /**
     * 更新收藏信息
     *
     * @param bookId 图书id
     * @param userId 用户id
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "更新收藏信息")
    @PreAuthorize("hasAuthority('book_collect:update')")
    @GetMapping("/update/{bookId}/{userId}")
    public Result updateByIds(
            @ApiParam(value = "图书id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer bookId,
            @ApiParam(value = "用户id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer userId) {
        Integer code = bookCollectService.updateByIds(bookId, userId);
        return Result.ok().data("code", code);
    }


    /**
     * 获取图书收藏状态
     *
     * @param bookId 图书id
     * @param userId 用户id
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "获取图书收藏状态")
    @PreAuthorize("hasAuthority('book_collect:query')")
    @GetMapping("/getBookCollectByIds/{bookId}/{userId}")
    public Result getBookCollectByIds(
            @ApiParam(value = "图书id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer bookId,
            @ApiParam(value = "用户id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer userId) {
        Integer code = bookCollectService.getBookCollectByIds(bookId, userId);
        return Result.ok().data("code", code);
    }


    /**
     * 获取图书收藏列表
     *
     * @param userId 用户id
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "获取图书收藏列表")
    @PreAuthorize("hasAuthority('book_collect:query')")
    @GetMapping("/getCollectedBooks/{userId}")
    public Result getCollectedBooks(
            @ApiParam(value = "用户id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer userId) {
        List<BookListDTO> bookList = bookCollectService.getCollectedBooks(userId);
        return Result.ok().data("bookList", bookList);
    }
}
