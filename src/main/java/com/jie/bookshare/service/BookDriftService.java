package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.BookDrift;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
public interface BookDriftService extends IService<BookDrift> {

    /**
     * 修改图书漂流状态
     * @param bookId
     * @param status
     */
    Integer changeBookStatus(Integer bookId, Integer status);

    /**
     * 根据bookId查询第一个漂流数据
     * @param bookId
     * @return
     */
    BookDrift getBookFirstDrift(Integer bookId);

    /**
     * 根据bookId查询对后一个漂流数据
     * @param bookId
     * @return
     */
    BookDrift getBookLastDrift(Integer bookId);

    /**
     * 图书共享，保存信息
     * @param reqBody
     * @return
     */
    Boolean releaseBook(Map<String, Object> reqBody);
}
