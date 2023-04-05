package com.jie.bookshare.controller;


import com.jie.bookshare.common.CommonConstant;
import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.BookCategory;
import com.jie.bookshare.entity.dto.BookCategoryCascaderDTO;
import com.jie.bookshare.filter.ddos.AccessLimit;
import com.jie.bookshare.service.BookCategoryService;
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
 * 图书目录Controller
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Validated
@RestController
@RequestMapping("/book-category")
@Api(tags = "图书分类相关")
public class BookCategoryController {

    @Resource
    private BookCategoryService bookCategoryService;

    /**
     * 获取所有一级目录
     *
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "获取所有一级目录")
    @PreAuthorize("hasAuthority('book_category:query')")
    @Deprecated
    @GetMapping("/getTopCategories")
    public Result getTopCategories() {
        List<BookCategory> data = bookCategoryService.getTopCategories();
        return Result.ok().data("categories", data);
    }

    /**
     * 获取一二级图书分类，以级联方式返回
     *
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "获取一二级图书分类，以级联方式返回")
    @PreAuthorize("hasAuthority('book_category:query')")
    @GetMapping("/getCategoryCascader")
    public Result getCategoryCascader() {
        List<BookCategoryCascaderDTO> options = bookCategoryService.getCategoryCascader();
        return Result.ok().data("options", options);
    }

    /**
     * 根据id获取分类全名（包括父分类，以"/"分隔）
     *
     * @param categoryId 目录id
     * @return
     */
    @AccessLimit(seconds = CommonConstant.REQUEST_SECONDS, maxCount = CommonConstant.REQUEST_MAX_COUNT)
    @ApiOperation(value = "根据id获取分类全名")
    @PreAuthorize("hasAuthority('book_category:query')")
    @GetMapping("/getCategoryFullName/{categoryId}")
    public Result getCategoryFullName(
            @ApiParam(value = "图书分类id", example = "0")
            @Digits(integer = 3, fraction = 0)
                                      @PathVariable Integer categoryId) {
        String categoryFullName = bookCategoryService.getCategoryFullName(categoryId);
        return Result.ok().data("categoryFullName", categoryFullName);
    }
}
