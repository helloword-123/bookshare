package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.*;
import com.jie.bookshare.entity.dto.BookBorrowDTO;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.entity.dto.CheckBookDriftDTO;
import com.jie.bookshare.entity.dto.DriftingBookDTO;
import com.jie.bookshare.exception.CustomizeRuntimeException;
import com.jie.bookshare.mapper.*;
import com.jie.bookshare.mq.MQMessage;
import com.jie.bookshare.mq.MessageProducer;
import com.jie.bookshare.service.BookDriftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Service
public class BookDriftServiceImpl extends ServiceImpl<BookDriftMapper, BookDrift> implements BookDriftService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BookDriftMapper bookDriftMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookDriftPictureMapper bookDriftPictureMapper;
    @Resource
    private DriftPictureMapper driftPictureMapper;
    @Resource
    private MessageProducer messageProducer;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DataSourceTransactionManager dsManager;
    @Resource
    TransactionDefinition definition;


    /**
     * 工具方法：合并book对象和bookDrift对象为bookListDTO对象
     *
     * @param book
     * @param bookDrift
     * @return
     */
    @Override
    public BookListDTO mergeBookAndBookDrift(Book book, BookDrift bookDrift) {
        BookListDTO bookListDTO = new BookListDTO();
        bookListDTO.setBookId(book.getId());
        bookListDTO.setName(book.getName());
        bookListDTO.setAuthor(book.getAuthor());
        bookListDTO.setPicture_url(book.getPictureUrl());
        bookListDTO.setCategoryId(book.getCategoryId());
        bookListDTO.setDetail(book.getDescription());
        bookListDTO.setPublishingHouse(book.getPublishingHouse());
        bookListDTO.setPublishingTime(book.getPublishingTime());
        bookListDTO.setIsbn(book.getIsbn());

        bookListDTO.setDriftId(bookDrift.getId());
        bookListDTO.setSharerId(bookDrift.getSharerId());
        bookListDTO.setSharerPhone(bookDrift.getSharerPhone());
        bookListDTO.setSharer(bookDrift.getSharerName());
        bookListDTO.setLocation(bookDrift.getDriftAddress());
        bookListDTO.setLatitude(bookDrift.getLatitude());
        bookListDTO.setLongitude(bookDrift.getLongitude());
        bookListDTO.setNote(bookDrift.getNote());
        bookListDTO.setReleaseTime(bookDrift.getReleaseTime());
        bookListDTO.setStatus(bookDrift.getStatus());
        bookListDTO.setBorrowerId(bookDrift.getBorrowerId());
        bookListDTO.setDriftTime(bookDrift.getDriftTime());
        bookListDTO.setDriftNum(bookDrift.getDriftNum());
        bookListDTO.setImgList(bookDriftMapper.getDriftPicturesByDriftId(bookDrift.getId()));

        return bookListDTO;
    }

    @Override
    public Integer changeBookStatus(Integer bookId, Integer status) {
        logger.info("Change book status. BookId is: {}, status: {}.", bookId, status);
        BookDrift bookDrift = getBookLastDrift(bookId);
        if (bookDrift == null) {
            logger.info("This bookId: {} does not has any drift record!", bookId);
            return 0;
        }
        bookDrift.setStatus(status);
        return baseMapper.updateById(bookDrift);
    }

    @Override
    public BookDrift getBookFirstDrift(Integer bookId) {
        logger.info("Get book's first drift record, bookId is: {}.", bookId);
        return bookDriftMapper.getBookFirstDrift(bookId);
    }

    @Override
    public BookDrift getBookLastDrift(Integer bookId) {
        logger.info("Get book's last drift record, bookId is: {}.", bookId);
        return bookDriftMapper.getBookLastDrift(bookId);
    }

    @Override
    public Boolean releaseBook(Map<String, Object> reqBody) {
        logger.info("Release book, bookInfo is: {}.", reqBody);
        // 手动开启事务
        TransactionStatus ts = null;
        try {
            //方式一：使用默认bean对象TransactionDefinition
            ts = dsManager.getTransaction(definition);

            // 保存图书信息
            // 1.查询改ISBN号的图书是否已经存在于book中，不存在则插入新数据
            LambdaQueryWrapper<Book> con1 = new LambdaQueryWrapper<>();
            String isbn = (String) reqBody.get("code");
            con1.eq(Book::getIsbn, isbn);
            Book book = bookMapper.selectOne(con1);
            if (book == null) {
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
            bookDrift.setLatitude((Double) reqBody.get("latitude"));
            bookDrift.setLongitude((Double) reqBody.get("longitude"));
            bookDrift.setSharerName((String) reqBody.get("userName"));
            bookDrift.setSharerPhone((String) reqBody.get("phoneNumber"));
            bookDrift.setNote((String) reqBody.get("will"));
            bookDrift.setReleaseTime(new Date());
            bookDrift.setStatus(0); // 发布审核

            BookDrift bookLastDrift = this.getBookLastDrift(book.getId());
            if (bookLastDrift == null) {
                logger.info("The bookId: {} is first drift.", book.getId());
                bookDrift.setDriftNum(1);   // 首次漂流
            } else {
                bookDrift.setDriftNum(bookLastDrift.getDriftNum() + 1);
            }

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
        } catch (Exception e) {
            e.printStackTrace();
            if (ts != null) {
                dsManager.rollback(ts);
            }
            return false;
        }

        return true;
    }

    /**
     * 地图搜索页获取正在漂流的图书信息
     *
     * @return
     */
    @Override
    public List<DriftingBookDTO> getDriftingBooks() {
        logger.info("Get drifting books.");
        LambdaQueryWrapper<BookDrift> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookDrift::getStatus, 1);
        List<BookDrift> bookDrifts = bookDriftMapper.selectList(con1);

        List<DriftingBookDTO> list = new ArrayList<>();
        for (BookDrift bookDrift : bookDrifts) {
            DriftingBookDTO dto = new DriftingBookDTO();
            dto.setId(bookDrift.getId());
            dto.setAddress(bookDrift.getDriftAddress());
            dto.setLatitude(bookDrift.getLatitude());
            dto.setLongitude(bookDrift.getLongitude());

            list.add(dto);
        }
        logger.info("Drifting books is: {}.", list);

        return list;
    }

    /**
     * 根据id获取正在漂流的信息
     *
     * @param id
     * @return
     */
    @Override
    public BookListDTO getDriftingById(Integer id) {
        logger.info("Get drifting book by bookId: {}.", id);
        LambdaQueryWrapper<BookDrift> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookDrift::getId, id).eq(BookDrift::getStatus, 1);
        BookDrift bookDrift = bookDriftMapper.selectOne(con1);
        Book book = bookMapper.selectById(bookDrift.getBookId());

        BookListDTO dto = this.mergeBookAndBookDrift(book, bookDrift);
        logger.info("BookListDTO is: {}.", dto);

        return dto;
    }

    /**
     * 借书
     *
     * @param dto
     */
    @Override
    public void borrowBook(BookBorrowDTO dto) {
        logger.info("Borrow book, BookBorrowDTO is: {}.", dto);
        BookDrift bookDrift = new BookDrift();
        bookDrift.setId(dto.getDriftId());
        BookDrift drift = bookDriftMapper.selectById(bookDrift);
        if (drift.getStatus() != 1) {
            throw new CustomizeRuntimeException("图书不在共享中！");
        }

        // redis获取锁
        String key = "lock_bookDrift_" + dto.getDriftId();
        boolean res = redisTemplate.opsForValue().setIfAbsent(key, 1);
        if (!res) {
            throw new CustomizeRuntimeException("他人正在共享此书！");
        }

        bookDrift.setBorrowerId(dto.getBorrowId());
        bookDrift.setStatus(3);
        bookDrift.setDriftTime(new Date());

        bookDriftMapper.updateById(bookDrift);
        // 释放锁
        redisTemplate.delete(key);
    }

    /**
     * 根据用户id获取他的共享和借阅记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<List<BookListDTO>> getShareBorrowBookList(Integer userId) {
        logger.info("Get share and borrow bookList by userId: {}.", userId);
        List<List<BookListDTO>> res = new ArrayList<>();

        // 共享记录
        LambdaQueryWrapper<BookDrift> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookDrift::getSharerId, userId);
        List<BookDrift> bookDrifts1 = bookDriftMapper.selectList(con1);
        List<BookListDTO> list1 = new ArrayList<>();
        for (BookDrift bookDrift : bookDrifts1) {
            Book book = bookMapper.selectById(bookDrift.getBookId());
            BookListDTO bookListDTO = this.mergeBookAndBookDrift(book, bookDrift);
            list1.add(bookListDTO);
        }

        // 借阅记录
        LambdaQueryWrapper<BookDrift> con2 = new LambdaQueryWrapper<>();
        con2.eq(BookDrift::getBorrowerId, userId);
        List<BookDrift> bookDrifts2 = bookDriftMapper.selectList(con2);
        List<BookListDTO> list2 = new ArrayList<>();
        for (BookDrift bookDrift : bookDrifts2) {
            Book book = bookMapper.selectById(bookDrift.getBookId());
            BookListDTO bookListDTO = this.mergeBookAndBookDrift(book, bookDrift);
            list2.add(bookListDTO);
        }

        res.add(list1);
        res.add(list2);
//        logger.info("Share and borrow bookList is: {}.", res);

        return res;
    }

    /**
     * 根据图书id获取其漂流记录（顺序连起来）
     *
     * @param bookId
     * @return
     */
    @Override
    public List<BookListDTO> getBookDriftSeries(Integer bookId) {
        logger.info("Get bookDrift series by bookId: {}.", bookId);
        List<BookListDTO> res = new ArrayList<>();

        Book book = bookMapper.selectById(bookId);

        LambdaQueryWrapper<BookDrift> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookDrift::getBookId, bookId).orderByAsc(BookDrift::getDriftNum);
        List<BookDrift> bookDrifts = bookDriftMapper.selectList(con1);
        for (BookDrift bookDrift : bookDrifts) {
            BookListDTO bookListDTO = this.mergeBookAndBookDrift(book, bookDrift);
            if (bookDrift.getBorrowerId() != null) {
                User borrower = userMapper.selectById(bookDrift.getBorrowerId());
                bookListDTO.setBorrowerName(borrower.getUserName());
            }
            res.add(bookListDTO);
        }
        logger.info("BookDrift series is: {}.", res);

        return res;
    }

    /**
     * 审核
     *
     * @param dto
     * @return
     */
    @Override
    public Integer checkBookDrift(CheckBookDriftDTO dto) {
        logger.info("Check bookDrift record, CheckBookDriftDTO is: {}.", dto);
        BookDrift bookDrift = bookDriftMapper.selectById(dto.getId());
        if (bookDrift == null) {
            return 0;
        }
        bookDrift.setStatus(dto.getStatus());
        bookDrift.setCheckerReply(dto.getCheckerReply());
        bookDrift.setCheckTime(new Date());

        // 推送消息到消息队列
        MQMessage mqMessage = new MQMessage(0, dto.getCheckerId(), bookDrift.getSharerId(), new Date());
        mqMessage.addData("title", "图书分享审核结果");
        mqMessage.addData("message", (dto.getStatus() == 1 ? "审核通过：" : "审核不通过：") + dto.getCheckerReply());
        messageProducer.lPush(mqMessage);
        logger.info("Push a new message: {} to mq.", mqMessage);

        return baseMapper.updateById(bookDrift);
    }

    /**
     * 获取未审核的图书
     *
     * @return
     */
    @Override
    public List<BookListDTO> getNotCheckedBooks() {
        logger.info("Get not checked books.");
        List<BookListDTO> list = new ArrayList<>();

        // 共享记录
        LambdaQueryWrapper<BookDrift> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookDrift::getStatus, 0);
        List<BookDrift> bookDrifts1 = bookDriftMapper.selectList(con1);
        for (BookDrift bookDrift : bookDrifts1) {
            Book book = bookMapper.selectById(bookDrift.getBookId());
            BookListDTO bookListDTO = this.mergeBookAndBookDrift(book, bookDrift);
            list.add(bookListDTO);
        }
        logger.info("Not checked books is: {}.", list);

        return list;
    }
}
