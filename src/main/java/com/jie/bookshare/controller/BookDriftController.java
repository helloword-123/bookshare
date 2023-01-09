package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.service.BookDriftService;
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
@RequestMapping("/book-drift")
public class BookDriftController {

    @Autowired
    private BookDriftService bookDriftService;

    @GetMapping("/checkBook/{bookId}/{status}")
    public Result checkBook(@PathVariable Integer bookId, @PathVariable Integer status ){

        Integer res = bookDriftService.changeBookStatus(bookId, status);

        if(res == 0){
            return Result.error().message("更新图书漂流状态失败！");
        }
        return Result.ok();
    }

    @PostMapping("/shareBook")
    public Result shareBook(@RequestBody Map<String, Object> reqBody){

        Boolean res = bookDriftService.releaseBook(reqBody);

        if(!res){
            return Result.error().message("发布图书信息失败！");
        }
        return Result.ok();
    }
}
