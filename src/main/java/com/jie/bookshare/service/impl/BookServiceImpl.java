package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.BookDrift;
import com.jie.bookshare.mapper.BookDriftMapper;
import com.jie.bookshare.mapper.BookMapper;
import com.jie.bookshare.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookDriftMapper bookDriftMapper;

    @Override
    public Boolean checkIsbnIsDrifting(String isbn) {
        // 根据isbn查询book的id
        LambdaQueryWrapper<Book> con1 = new LambdaQueryWrapper<>();
        con1.eq(Book::getIsbn, isbn);
        Book book = baseMapper.selectOne(con1);
        if(book == null){
            return true;
        }

        // 查询改bookid是否正在漂流中
        LambdaQueryWrapper<BookDrift> con2 = new LambdaQueryWrapper<>();
        // 有bookid的而且status < 4的漂流记录，说明此书正在共享中
        con2.eq(BookDrift::getBookId, book.getId()).lt(BookDrift::getStatus, 4);
        List<BookDrift> bookDrifts = bookDriftMapper.selectList(con2);
        return bookDrifts == null;
    }
}
