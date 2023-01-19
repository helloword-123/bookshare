package com.jie.bookshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jie.bookshare.entity.BookDrift;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
public interface BookDriftMapper extends BaseMapper<BookDrift> {


    /**
     * 查询书的第一次漂流信息
     * @param bookId
     * @return
     */
    BookDrift getBookFirstDrift(Integer bookId);

    /**
     * 查询书的第最后一次漂流信息
     * @param bookId
     * @return
     */
    BookDrift getBookLastDrift(Integer bookId);

    /**
     * 查询正在漂流的图书id
     * @return
     */
    List<BookDrift> selectDriftingBooks();

    /**
     * 根据漂流id获取漂流图片
     * @param driftId
     * @return
     */
    List<String> getDriftPicturesByDriftId(Integer driftId);
}
