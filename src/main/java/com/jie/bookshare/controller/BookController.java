package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/isDrifting/{isbn}")
    public Result checkIsbnIsDrifting(@PathVariable String isbn){

        Boolean res = bookService.checkIsbnIsDrifting(isbn);

        // 图书漂流正在进行中，无法重复漂流
        if(!res){
            return Result.ok().code(20002);
        }
        return Result.ok();
    }
}
