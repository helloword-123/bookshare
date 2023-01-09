package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.Book;

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
}
