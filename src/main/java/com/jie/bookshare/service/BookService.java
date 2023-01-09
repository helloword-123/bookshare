package com.jie.bookshare.service;

import com.jie.bookshare.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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
     * 图书共享，保存信息
     * @param reqBody
     * @return
     */
    Boolean     saveBook(Map<String, Object> reqBody);

    /**
     * 修改图书状态
     * @param bookId
     * @param status
     */
    Integer changeBookStatus(Integer bookId, Integer status);
}
