package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.service.BookCollectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 图书收藏Controller
 *
 * @author wuhaojie
 * @since 2023-02-14
 */
@RestController
@RequestMapping("/book-collect")
public class BookCollectController {

    @Resource
    private BookCollectService bookCollectService;

    /**
     * 更新收藏信息
     * @param bookId    图书id
     * @param userId    用户id
     * @return
     */
    @PreAuthorize("hasAuthority('book_collect:update')")
    @GetMapping("/update/{bookId}/{userId}")
    public Result updateByIds(@PathVariable Integer bookId, @PathVariable Integer userId){
        Integer code = bookCollectService.updateByIds(bookId, userId);

        return Result.ok().data("code", code);
    }


    /**
     * 获取图书收藏状态
     * @param bookId    图书id
     * @param userId    用户id
     * @return
     */
    @PreAuthorize("hasAuthority('book_collect:query')")
    @GetMapping("/getBookCollectByIds/{bookId}/{userId}")
    public Result getBookCollectByIds(@PathVariable Integer bookId, @PathVariable Integer userId){
        Integer code = bookCollectService.getBookCollectByIds(bookId, userId);

        return Result.ok().data("code", code);
    }


    /**
     * 获取图书收藏列表
     * @param userId    用户id
     * @return
     */
    @PreAuthorize("hasAuthority('book_collect:query')")
    @GetMapping("/getCollectedBooks/{userId}")
    public Result getCollectedBooks(@PathVariable Integer userId){
        List<BookListDTO> bookList = bookCollectService.getCollectedBooks(userId);

        return Result.ok().data("bookList", bookList);
    }
}
