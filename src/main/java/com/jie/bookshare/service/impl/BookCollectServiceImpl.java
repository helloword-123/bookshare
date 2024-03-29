package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.BookCollect;
import com.jie.bookshare.entity.BookDrift;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.mapper.BookCollectMapper;
import com.jie.bookshare.mapper.BookDriftMapper;
import com.jie.bookshare.mapper.BookMapper;
import com.jie.bookshare.service.BookCollectService;
import com.jie.bookshare.service.BookDriftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-02-14
 */
@Service
public class BookCollectServiceImpl extends ServiceImpl<BookCollectMapper, BookCollect> implements BookCollectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BookCollectMapper bookCollectMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookDriftService bookDriftService;

    @Resource
    private BookDriftMapper bookDriftMapper;

    /**
     * @param userId
     * @return
     */
    @Override
    public List<BookListDTO> getCollectedBooks(Integer userId) {
        logger.info("Get collected books by userId: {}.", userId);
        List<BookListDTO> res = new ArrayList<>();

        List<Integer> collectBookIds = bookCollectMapper.getCollectedBooks(userId);

        for (Integer bookId : collectBookIds) {
            BookDrift bookDrift = bookDriftMapper.getBookLastDrift(bookId);
            Book book = bookMapper.selectById(bookDrift.getBookId());
            BookListDTO bookListDTO = bookDriftService.mergeBookAndBookDrift(book, bookDrift);
            res.add(bookListDTO);
        }
        logger.info("Collected books is: {}.", res);
        return res;
    }

    /**
     * @param bookId
     * @param userId
     * @return
     */
    @Override
    public Integer getBookCollectByIds(Integer bookId, Integer userId) {
        logger.info("Get BookCollect status By bookId: {}, userId: {}.", bookId, userId);
        LambdaQueryWrapper<BookCollect> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookCollect::getBookId, bookId).eq(BookCollect::getUserId, userId);
        BookCollect bookCollect = bookCollectMapper.selectOne(con1);
        if(bookCollect == null){
            logger.info("bookCollect record does not exist!");
            return 0;
        }
        return bookCollect.getStatus();
    }

    /**
     * 更新收藏信息
     *
     * @param bookId
     * @param userId
     * @return
     */
    @Override
    public Integer updateByIds(Integer bookId, Integer userId) {
        logger.info("Update bookCollect by bookId: {}, userId: {}.", bookId, userId);
        int code = 0;
        LambdaQueryWrapper<BookCollect> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookCollect::getBookId, bookId).eq(BookCollect::getUserId, userId);
        BookCollect bookCollect = bookCollectMapper.selectOne(con1);
        if(bookCollect == null){
            BookCollect bc = new BookCollect();
            bc.setBookId(bookId);
            bc.setUserId(userId);
            bc.setStatus(1);
            bookCollectMapper.insert(bc);
            return 1;
        }

        if(bookCollect.getStatus() == 1){
            bookCollect.setStatus(2);
            code = 2;
        } else {
            bookCollect.setStatus(1);
            code = 1;
        }
        bookCollectMapper.update(bookCollect, con1);
        logger.info("BookCollect's status is: {}.", bookCollect.getStatus());

        return code;
    }
}
