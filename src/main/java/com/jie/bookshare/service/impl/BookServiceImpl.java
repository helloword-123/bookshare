package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.BookDetail;
import com.jie.bookshare.mapper.BookDetailMapper;
import com.jie.bookshare.mapper.BookMapper;
import com.jie.bookshare.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundKeyOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

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
    private BookDetailMapper bookDetailMapper;



    @Override
    @Transactional
    public Boolean saveBook(Map<String, Object> reqBody) {
        // 保存图书信息
        // 1.查询改ISBN号的图书是否已经存在于book_detail中，不存在则插入新数据
        LambdaQueryWrapper<BookDetail> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookDetail::getIsbn, (String)reqBody.get("code"));
        BookDetail bookDetail = bookDetailMapper.selectOne(con1);
        if(bookDetail == null){
            // 插入信息
            bookDetail = new BookDetail();
            bookDetail.setBookName((String) reqBody.get("name"));
            bookDetail.setAuthor((String) reqBody.get("author"));
            bookDetail.setPublicationTime((String) reqBody.get("published"));
            bookDetail.setPublishingHouse((String) reqBody.get("publishing"));
            bookDetail.setPictureUrl((String) reqBody.get("photoUrl"));
            bookDetail.setDescription((String) reqBody.get("description"));
            bookDetailMapper.insert(bookDetail);
        }


        // 2.插入book信息
        Book book = new Book();
        // 每次共享都会插入一个新的book对象，而bookDetail可能是相同的
        book.setBookDetailId(bookDetail.getId());
        book.setStatus(0);
        book.setCurrentAddress((String) reqBody.get("location"));
        book.setPublisherId((Integer) reqBody.get("userId"));
        book.setSharerId((Integer) reqBody.get("userId"));
        book.setReleaseTime(new Date());
        baseMapper.insert(book);


        // 3.插入图书漂流信息

        return true;
    }

    @Override
    public Integer changeBookStatus(Integer bookId, Integer status) {
        Book book = new Book();
        book.setId(bookId);
        book.setStatus(status);
        return baseMapper.updateById(book);
    }
}
