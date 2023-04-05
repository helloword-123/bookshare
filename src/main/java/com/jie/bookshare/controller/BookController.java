package com.jie.bookshare.controller;


import com.jie.bookshare.common.Result;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.entity.dto.BookListWithCategoryDTO;
import com.jie.bookshare.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 图书Controller
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Validated
@RestController
@RequestMapping("/book")
@Api(tags = "图书相关")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 小程序首页获取图书数据，根据分类id聚合返回
     *
     * @return
     */
    @ApiOperation(value = "小程序首页获取图书数据，根据分类id聚合返回")
    @PreAuthorize("hasAuthority('book:query')")
    @GetMapping("/getListWithCategory")
    public Result getListWithCategory() {
        List<BookListWithCategoryDTO> bookList = bookService.getListWithCategory();
        return Result.ok().data("bookList", bookList);
    }

    /**
     * 判断改isbn号的图书是否已经在漂流中
     *
     * @param isbn 图书ISBN码
     * @return
     */
    @ApiOperation(value = "判断改isbn号的图书是否已经在漂流中")
    @PreAuthorize("hasAuthority('book:query')")
    @GetMapping("/isDrifting/{isbn}")
    public Result checkIsbnIsDrifting(
            @ApiParam(value = "isbn号", example = "9787302511052")
            @Pattern(regexp = "^[0-9]{10}|[0-9]{13}$", message = "isbn码格式不符合要求")
            @PathVariable String isbn) {
        Boolean res = bookService.checkIsbnIsDrifting(isbn);
        // 图书漂流正在进行中，无法重复漂流
        if (!res) {
            return Result.ok().code(20002);
        }
        return Result.ok();
    }

    /**
     * 根据筛选条件返回图书列表
     *
     * @param categoryId 图书分类id
     * @param sortColumn 排序字段
     * @param sortOrder  升序/降序
     * @param keyword    模糊查询关键词
     * @return
     */
    @ApiOperation(value = "根据筛选条件返回图书列表")
    @PreAuthorize("hasAuthority('book:query')")
    @GetMapping("/getListWithCondition")
    public Result getListWithCondition(
            @ApiParam(value = "分类ID，非必需", example = "0")
            @RequestParam(required = false) Integer categoryId,
            @ApiParam(value = "排序字段，可选值：distance，releaseTime, 非必需", example = "distance")
            @RequestParam(required = false) String sortColumn,
            @ApiParam(value = "升序还是降序，可选值：asc、desc，非必需", example = "asc")
            @RequestParam(required = false) String sortOrder,
            @ApiParam(value = "模糊查询关键字，非必需", example = "XXX")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "用户当前定位纬度，非必需", example = "0")
            @RequestParam(required = false) Double latitude,
            @ApiParam(value = "用户当前定位精度，非必需", example = "0")
            @RequestParam(required = false) Double longitude) {
        // 校验
        if (sortColumn != null && !"distance".equals(sortColumn) && !"releaseTime".equals(sortColumn)) {
            return Result.error().message("排序字段不符合要求！");
        }
        if (sortOrder != null && !"asc".equals(sortOrder) && !"desc".equals(sortOrder)) {
            return Result.error().message("排序方式不符合要求！");
        }
        List<BookListDTO> bookList = bookService.getListWithCondition(categoryId, sortColumn, sortOrder, keyword, latitude, longitude);
        return Result.ok().data("list", bookList);
    }
}
