package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.BookCollect;
import com.jie.bookshare.entity.dto.BookListDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-02-14
 */
public interface BookCollectService extends IService<BookCollect> {

    /**
     * 更新收藏信息
     * @param bookId
     * @param userId
     * @return
     */
    Integer updateByIds(Integer bookId, Integer userId);

    Integer getBookCollectByIds(Integer bookId, Integer userId);

    List<BookListDTO> getCollectedBooks(Integer userId);
}
