package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.BookDrift;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.entity.dto.DriftingBookDTO;

import java.util.List;
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

    /**
     * 地图搜索页获取正在漂流的图书信息
     * @return
     */
    List<DriftingBookDTO> getDriftingBooks();

    /**
     * 根据id获取正在漂流的信息
     * @param id
     * @return
     */
    BookListDTO getDriftingById(Integer id);
}
