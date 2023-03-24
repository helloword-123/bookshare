package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.BookCategory;
import com.jie.bookshare.entity.dto.BookCategoryCascaderDTO;
import com.jie.bookshare.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 图书目录Controller
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@RestController
@RequestMapping("/book-category")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;

    /**
     * 获取所有一级目录
     *
     * @return
     */
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
    @PreAuthorize("hasAuthority('book_category:query')")
    @GetMapping("/getCategoryFullName/{categoryId}")
    public Result getCategoryFullName(@PathVariable Integer categoryId) {
        String categoryFullName = bookCategoryService.getCategoryFullName(categoryId);
        return Result.ok().data("categoryFullName", categoryFullName);
    }
}
