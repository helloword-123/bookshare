package com.jie.bookshare.mapper;

import com.jie.bookshare.entity.BookDrift;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("select * from book_drift \n" +
            "where book_id = 1 and\n" +
            "drift_num = (\n" +
            " select min(drift_num) from book_drift\n" +
            ");")
    BookDrift getBookFirstDrift(Integer bookId);

    /**
     * 查询书的第最后一次漂流信息
     * @param bookId
     * @return
     */
    @Select("select * from book_drift \n" +
            "where book_id = 1 and\n" +
            "drift_num = (\n" +
            " select max(drift_num) from book_drift\n" +
            ");")
    BookDrift getBookLastDrift(Integer bookId);
}
