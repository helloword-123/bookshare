package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.entity.dto.DriftingBookDTO;
import com.jie.bookshare.service.BookDriftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * 修改图书漂流状态
     * @param bookId
     * @param status
     * @return
     */
    @PutMapping("/checkBook/{bookId}/{status}")
    public Result checkBook(@PathVariable Integer bookId, @PathVariable Integer status ){

        Integer res = bookDriftService.changeBookStatus(bookId, status);

        if(res == 0){
            return Result.error().message("更新图书漂流状态失败！");
        }
        return Result.ok();
    }

    /**
     * 图书共享，保存信息
     * @param reqBody
     * @return
     */
    @PostMapping("/shareBook")
    public Result shareBook(@RequestBody Map<String, Object> reqBody){

        Boolean res = bookDriftService.releaseBook(reqBody);

        if(!res){
            return Result.error().message("发布图书信息失败！");
        }
        return Result.ok();
    }

    /**
     * 地图搜索页获取正在漂流的图书信息
     * @return
     */
    @GetMapping("/getDriftingBooks")
    public Result getDriftingBooks() {
        List<DriftingBookDTO> driftingBookDTOS = bookDriftService.getDriftingBooks();

        return Result.ok().data("driftingBooks", driftingBookDTOS);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/getDriftingById/{id}")
    public Result getById(@PathVariable Integer id) {
        BookListDTO bookListDTO = bookDriftService.getDriftingById(id);

        return Result.ok().data("bookDrift", bookListDTO);
    }
}
