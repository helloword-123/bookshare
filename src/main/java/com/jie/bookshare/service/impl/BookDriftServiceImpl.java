package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.BookDrift;
import com.jie.bookshare.entity.BookDriftPicture;
import com.jie.bookshare.entity.DriftPicture;
import com.jie.bookshare.mapper.BookDriftMapper;
import com.jie.bookshare.mapper.BookDriftPictureMapper;
import com.jie.bookshare.mapper.BookMapper;
import com.jie.bookshare.mapper.DriftPictureMapper;
import com.jie.bookshare.service.BookDriftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
public class BookDriftServiceImpl extends ServiceImpl<BookDriftMapper, BookDrift> implements BookDriftService {

    @Autowired
    private BookDriftMapper bookDriftMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookDriftPictureMapper bookDriftPictureMapper;
    @Autowired
    private DriftPictureMapper driftPictureMapper;

    @Autowired
    private DataSourceTransactionManager dsManager;
    @Autowired
    TransactionDefinition definition;

    @Override
    public Integer changeBookStatus(Integer bookId, Integer status) {
        BookDrift bookDrift = getBookLastDrift(bookId);
        if(bookDrift == null){
            return 0;
        }
        bookDrift.setStatus(status);
        return baseMapper.updateById(bookDrift);
    }

    @Override
    public BookDrift getBookFirstDrift(Integer bookId) {

        return bookDriftMapper.getBookFirstDrift(bookId);
    }

    @Override
    public BookDrift getBookLastDrift(Integer bookId) {
        return bookDriftMapper.getBookLastDrift(bookId);
    }

    @Override
    public Boolean releaseBook(Map<String, Object> reqBody) {
        // 手动开启事务
        TransactionStatus ts = null;
        try {
            //方式一：使用默认bean对象TransactionDefinition
            ts =dsManager.getTransaction(definition);

            // 保存图书信息
            // 1.查询改ISBN号的图书是否已经存在于book中，不存在则插入新数据
            LambdaQueryWrapper<Book> con1 = new LambdaQueryWrapper<>();
            String isbn = (String)reqBody.get("code");
            con1.eq(Book::getIsbn, isbn);
            Book book = bookMapper.selectOne(con1);
            if(book == null){
                // 插入信息
                book = new Book();
                book.setIsbn(isbn);
                book.setName((String) reqBody.get("name"));
                book.setAuthor((String) reqBody.get("author"));
                book.setPublishingTime((String) reqBody.get("published"));
                book.setPublishingHouse((String) reqBody.get("publishing"));
                book.setPictureUrl((String) reqBody.get("photoUrl"));
                book.setDescription((String) reqBody.get("description"));
                book.setCategoryId((Integer) reqBody.get("cascaderValue"));
                bookMapper.insert(book);
            }


            // 2.插入图书漂流信息
            BookDrift bookDrift = new BookDrift();
            bookDrift.setBookId(book.getId());
            bookDrift.setSharerId((Integer) reqBody.get("userId"));
            bookDrift.setDriftAddress((String) reqBody.get("location"));
            bookDrift.setSharerName((String) reqBody.get("userName"));
            bookDrift.setSharerPhone((String) reqBody.get("phoneNumber"));
            bookDrift.setNote((String) reqBody.get("will"));
            bookDrift.setReleaseTime(new Date());
            bookDrift.setStatus(0); // 发布审核
            bookDrift.setDriftNum(1);   // 首次漂流
            baseMapper.insert(bookDrift);


            // 3.插入发布图片
            List<LinkedHashMap<String, String>> pictures = (List<LinkedHashMap<String, String>>) reqBody.get("fileList");
            for (LinkedHashMap<String, String> p : pictures) {
                DriftPicture picture = new DriftPicture();
                picture.setPictureUrl(p.get("url"));
                driftPictureMapper.insert(picture);
                BookDriftPicture bookDriftPicture = new BookDriftPicture();
                bookDriftPicture.setDriftId(bookDrift.getId());
                bookDriftPicture.setPictureId(picture.getId());
                bookDriftPictureMapper.insert(bookDriftPicture);
            }

            // 提交
            dsManager.commit(ts);
        } catch (Exception e){
            e.printStackTrace();
            if (ts != null) {
                dsManager.rollback(ts);
            }
            return false;
        }

        return true;
    }

}
