package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/shareBook")
    public Result shareBook(@RequestBody Map<String, Object> reqBody){

        Boolean res = bookService.saveBook(reqBody);

        if(!res){
            return Result.error().message("保存图书信息失败！");
        }
        return Result.ok();
    }

    @GetMapping("/checkBook/{bookId}/{status}")
    public Result checkBook(@PathVariable Integer bookId, @PathVariable Integer status ){

        Integer res = bookService.changeBookStatus(bookId, status);

        if(res == 0){
            return Result.error().message("更新图书状态失败！");
        }
        return Result.ok();
    }
}
