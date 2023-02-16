package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.BookDrift;
import com.jie.bookshare.entity.dto.BookBorrowDTO;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.entity.dto.CheckBookDriftDTO;
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

    /**
     * 借书
     * @param dto
     */
    void borrowBook(BookBorrowDTO dto);

    /**
     * 根据用户id获取他的共享和借阅记录
     * @param userId
     * @return
     */
    List<List<BookListDTO>> getShareBorrowBookList(Integer userId);

    /**
     * 工具方法：合并book对象和bookDrift对象为bookListDTO对象
     * @param book
     * @param bookDrift
     * @return
     */
    BookListDTO mergeBookAndBookDrift(Book book, BookDrift bookDrift);

    /**
     * 根据图书id获取其漂流记录（顺序连起来）
     * @param bookId
     * @return
     */
    List<BookListDTO> getBookDriftSeries(Integer bookId);

    /**
     * 审核
     * @param checkBookDriftDTO
     * @return
     */
    Integer checkBookDrift(CheckBookDriftDTO checkBookDriftDTO);

    List<BookListDTO> getNotCheckedBooks();
}
