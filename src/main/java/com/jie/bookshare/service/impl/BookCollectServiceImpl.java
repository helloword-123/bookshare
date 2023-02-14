package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.BookCollect;
import com.jie.bookshare.entity.BookDrift;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.mapper.BookCollectMapper;
import com.jie.bookshare.mapper.BookMapper;
import com.jie.bookshare.service.BookCollectService;
import com.jie.bookshare.service.BookDriftService;
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

    @Resource
    private BookCollectMapper bookCollectMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookDriftService bookDriftService;

    /**
     * @param userId
     * @return
     */
    @Override
    public List<BookListDTO> getCollectedBooks(Integer userId) {
        List<BookListDTO> res = new ArrayList<>();

        List<BookDrift> bookDrifts = bookCollectMapper.getCollectedBooks(userId);
        for (BookDrift bookDrift : bookDrifts) {
            Book book = bookMapper.selectById(bookDrift.getBookId());
            BookListDTO bookListDTO = bookDriftService.mergeBookAndBookDrift(book, bookDrift);
            res.add(bookListDTO);
        }

        return res;
    }

    /**
     * @param bookId
     * @param userId
     * @return
     */
    @Override
    public Integer getBookCollectByIds(Integer bookId, Integer userId) {
        LambdaQueryWrapper<BookCollect> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookCollect::getBookId, bookId).eq(BookCollect::getUserId, userId);
        BookCollect bookCollect = bookCollectMapper.selectOne(con1);
        if(bookCollect == null){
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
        Integer code = 0;

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

        return code;
    }
}
