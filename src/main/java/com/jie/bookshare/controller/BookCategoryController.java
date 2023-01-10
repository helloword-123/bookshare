package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.BookCategory;
import com.jie.bookshare.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
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
     * @return
     */
    @GetMapping("/getTopCategories")
    public Result getTopCategories(){
        List<BookCategory> data = bookCategoryService.getTopCategories();

        return Result.ok().data("categories", data);
    }
}
