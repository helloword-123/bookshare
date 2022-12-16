package com.jie.bookshare.service.impl;

import com.jie.bookshare.entity.Book;
import com.jie.bookshare.mapper.BookMapper;
import com.jie.bookshare.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
