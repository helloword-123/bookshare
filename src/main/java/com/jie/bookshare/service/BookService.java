package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.dto.BookListWithCategoryDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
public interface BookService extends IService<Book> {




    /**
     * 判断改isbn号的图书是否已经在漂流中
     * @param isbn
     * @return
     */
    Boolean checkIsbnIsDrifting(String isbn);

    /**
     * 小程序首页获取图书数据，根据分类id聚合返回
     * @return
     */
    List<BookListWithCategoryDTO> getListWithCategory();
}
