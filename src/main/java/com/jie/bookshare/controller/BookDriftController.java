package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.*;
import com.jie.bookshare.service.BookDriftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.util.List;
import java.util.Map;

/**
 * 图书漂流Controller
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Validated
@RestController
@RequestMapping("/book-drift")
@Api(tags = "图书漂流相关")
public class BookDriftController {

    @Autowired
    private BookDriftService bookDriftService;


    /**
     * 获取未审核的图书
     *
     * @return
     */
    @ApiOperation(value = "获取未审核的图书")
    @PreAuthorize("hasAuthority('book_drift:admin')")
    @GetMapping("/getNotCheckedBooks")
    public Result getNotCheckedBooks() {
        List<BookListDTO> bookList = bookDriftService.getNotCheckedBooks();
        return Result.ok().data("bookList", bookList);
    }


    /**
     * 审核
     *
     * @param checkBookDriftDTO 前端审核传输的dto
     * @return
     */
    @ApiOperation(value = "审核")
    @PreAuthorize("hasAuthority('book_drift:admin')")
    @PostMapping("/checkBook")
    public Result checkBookDrift(
            @ApiParam(value = "审核信息")
            @Valid
            @RequestBody CheckBookDriftDTO checkBookDriftDTO) {
        Integer res = bookDriftService.checkBookDrift(checkBookDriftDTO);
        if (res == 0) {
            return Result.error().message("更新图书漂流状态失败！");
        }
        return Result.ok();
    }


    /**
     * 修改图书漂流状态
     *
     * @param bookId 图书id
     * @param status 修改状态
     * @return
     */
    @ApiOperation(value = "修改图书漂流状态")
    @PreAuthorize("hasAuthority('book_drift:admin')")
    @PutMapping("/checkBook/{bookId}/{status}")
    public Result checkBook(
            @ApiParam(value = "图书id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer bookId,
            @ApiParam(value = "审核状态", example = "1")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer status) {
        Integer res = bookDriftService.changeBookStatus(bookId, status);
        if (res == 0) {
            return Result.error().message("更新图书漂流状态失败！");
        }
        return Result.ok();
    }

    /**
     * 图书共享，保存信息
     *
     * @param reqBody 前端共享图书传输的对象
     * @return
     */
    @ApiOperation(value = "图书共享")
    @PreAuthorize("hasAuthority('book_drift:add')")
    @PostMapping("/shareBook")
    public Result shareBook(
            @ApiParam(value = "图书共享信息")
            @RequestBody Map<String, Object> reqBody) {
        Boolean res = bookDriftService.releaseBook(reqBody);
        if (!res) {
            return Result.error().message("发布图书信息失败！");
        }
        return Result.ok();
    }

    /**
     * 地图搜索页获取正在漂流的图书信息
     *
     * @return
     */
    @ApiOperation(value = "地图搜索页获取正在漂流的图书信息")
    @PreAuthorize("hasAuthority('book_drift:query')")
    @GetMapping("/getDriftingBooks")
    public Result getDriftingBooks() {
        List<DriftingBookDTO> driftingBookDTOS = bookDriftService.getDriftingBooks();
        return Result.ok().data("driftingBooks", driftingBookDTOS);
    }

    /**
     * 根据id获取正在漂流的信息
     *
     * @param id 图书漂流id
     * @return
     */
    @ApiOperation(value = "根据id获取正在漂流的信息")
    @PreAuthorize("hasAuthority('book_drift:query')")
    @GetMapping("/getDriftingById/{id}")
    public Result getById(
            @ApiParam(value = "图书漂流id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer id) {
        BookListDTO bookListDTO = bookDriftService.getDriftingById(id);
        return Result.ok().data("bookDrift", bookListDTO);
    }

    /**
     * 借书
     *
     * @param dto 前端借阅图书传输的dto
     * @return
     */
    @ApiOperation(value = "借书")
    @PreAuthorize("hasAuthority('book_drift:update')")
    @PostMapping("/borrow")
    public Result borrowBook(
            @ApiParam(value = "图书借阅信息")
            @Valid
            @RequestBody BookBorrowDTO dto) {
        bookDriftService.borrowBook(dto);
        return Result.ok();
    }

    /**
     * 根据用户id获取他的共享和借阅记录
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "根据用户id获取他的共享和借阅记录")
    @PreAuthorize("hasAuthority('book_drift:query')")
    @GetMapping("/getShareBorrowBookList/{userId}")
    public Result getShareBorrowBookList(
            @ApiParam(value = "用户id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer userId) {
        // 第一个数组是共享记录；第二个数组是借阅记录
        List<List<BookListDTO>> bookList = bookDriftService.getShareBorrowBookList(userId);
        return Result.ok().data("bookList", bookList);
    }

    /**
     * 根据图书id获取其漂流记录（顺序连起来）
     *
     * @param bookId 用户id
     * @return
     */
    @ApiOperation(value = "根据图书id获取其漂流记录（顺序连起来）")
    @PreAuthorize("hasAuthority('book_drift:query')")
    @GetMapping("/getBookDriftSeries/{bookId}")
    public Result getBookDriftSeries(
            @ApiParam(value = "图书id", example = "0")
            @Digits(integer = 3, fraction = 0)
            @PathVariable Integer bookId) {
        List<BookListDTO> bookDriftSeries = bookDriftService.getBookDriftSeries(bookId);
        return Result.ok().data("bookDriftSeries", bookDriftSeries);
    }

}
