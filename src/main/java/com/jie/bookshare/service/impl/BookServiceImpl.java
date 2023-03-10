package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.Book;
import com.jie.bookshare.entity.BookCategory;
import com.jie.bookshare.entity.BookDrift;
import com.jie.bookshare.entity.dto.BookListDTO;
import com.jie.bookshare.entity.dto.BookListWithCategoryDTO;
import com.jie.bookshare.mapper.BookCategoryMapper;
import com.jie.bookshare.mapper.BookDriftMapper;
import com.jie.bookshare.mapper.BookMapper;
import com.jie.bookshare.mapper.UserMapper;
import com.jie.bookshare.service.BookDriftService;
import com.jie.bookshare.service.BookService;
import com.jie.bookshare.utils.LocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookDriftService bookDriftService;
    @Autowired
    private BookDriftMapper bookDriftMapper;
    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * 判断改isbn号的图书是否已经在漂流中
     *
     * @param isbn
     * @return
     */
    @Override
    public Boolean checkIsbnIsDrifting(String isbn) {
        // 根据isbn查询book的id
        LambdaQueryWrapper<Book> con1 = new LambdaQueryWrapper<>();
        con1.eq(Book::getIsbn, isbn);
        Book book = baseMapper.selectOne(con1);
        if (book == null) {
            return true;
        }

        // 查询改bookid是否正在漂流中
        // 1.漂流记录没有此书则返回true
        LambdaQueryWrapper<BookDrift> con3 = new LambdaQueryWrapper<>();
        con3.eq(BookDrift::getBookId, book.getId());
        List<BookDrift> bookDrifts1 = bookDriftMapper.selectList(con3);
        if (bookDrifts1.size() == 0) {
            return true;
        }

        // 2.有bookid的而且status < 4的漂流记录，说明此书正在共享中
        LambdaQueryWrapper<BookDrift> con2 = new LambdaQueryWrapper<>();
        con2.eq(BookDrift::getBookId, book.getId()).lt(BookDrift::getStatus, 4);
        List<BookDrift> bookDrifts = bookDriftMapper.selectList(con2);

        return bookDrifts.size() == 0;
    }

    /**
     * 小程序首页获取图书数据，根据分类id聚合返回
     *
     * @return
     */
    @Override
    public List<BookListWithCategoryDTO> getListWithCategory() {
        List<BookListWithCategoryDTO> list = new ArrayList<>();

        // 1.查询所有一级目录
        LambdaQueryWrapper<BookCategory> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookCategory::getPid, 0);
        List<BookCategory> categories = bookCategoryMapper.selectList(con1);

        // 2.根据一级目录id聚合图书数据（后续修改只返回部分）
        for (BookCategory category : categories) {
            BookListWithCategoryDTO dto = new BookListWithCategoryDTO();


            // 查询属于这个category_id的书籍
            // 2.1 先查一级目录的所有子目录
            List<Integer> subIds = bookCategoryMapper.selectSubCategoryIds(category.getId());

            // 2.2 查询正在漂流的图书id
            List<BookDrift> bookDrifts = bookDriftMapper.selectDriftingBooks();

            // 2.3 查询属于子目录的图书
            List<BookListDTO> bookListDTOS = new ArrayList<>();
            for (BookDrift bookDrift : bookDrifts) {
                Book book = bookMapper.selectById(bookDrift.getBookId());
                if (subIds.contains(book.getCategoryId())) {

                    BookListDTO bookListDTO = bookDriftService.mergeBookAndBookDrift(book, bookDrift);

                    bookListDTOS.add(bookListDTO);
                }
            }

            dto.setBookCategory(category);
            dto.setList(bookListDTOS);

            list.add(dto);
        }

        return list;
    }

    /**
     * 根据筛选条件返回图书列表
     *
     * @param categoryId
     * @param sortColumn
     * @param sortOrder
     * @param keyword
     * @return
     */
    @Override
    public List<BookListDTO> getListWithCondition(Integer categoryId, String sortColumn, String sortOrder, String keyword, Double latitude, Double longitude) {
        List<BookListDTO> bookListDTOS = new ArrayList<>();
        // 1.先根据categoryId和keyword查询列表
        // 查询正在漂流的图书id
        List<BookDrift> bookDrifts = bookDriftMapper.selectDriftingBooks();

        // 查询属于子目录的图书
        for (BookDrift bookDrift : bookDrifts) {
            // 关键字查询
            LambdaQueryWrapper<Book> con2 = new LambdaQueryWrapper<>();
            con2.eq(Book::getId, bookDrift.getBookId());
            if (categoryId != -1) {
                con2.eq(Book::getCategoryId, categoryId);
            }
            if (keyword != null) {
                con2.and(wrapper -> wrapper.like(Book::getName, keyword)
                        .or()
                        .like(Book::getAuthor, keyword));
            }
            Book book = bookMapper.selectOne(con2);

            if (book == null) {
                continue;
            }

            BookListDTO bookListDTO = bookDriftService.mergeBookAndBookDrift(book, bookDrift);

            bookListDTOS.add(bookListDTO);
        }

        // 2.再根据排序字段进行排序
        if (sortColumn == null) {
            return bookListDTOS;
        } else if (sortColumn.equals("distance")) {
            for (BookListDTO dto : bookListDTOS) {
                dto.setDistance(LocationUtils.getDistance(latitude, longitude, dto.getLatitude(), dto.getLongitude()));
            }
            if (sortOrder.equals("asc")) {
                bookListDTOS.sort(Comparator.comparing(BookListDTO::getDistance));
            } else if (sortOrder.equals("desc")) {
                bookListDTOS.sort(Comparator.comparing(BookListDTO::getDistance).reversed());
            }
        } else if (sortColumn.equals("releaseTime")) {
            // 时间值越大，则越近；所以发布时间近=asc
            if (sortOrder.equals("desc")) {
                bookListDTOS.sort(Comparator.comparing(BookListDTO::getReleaseTime));
            } else if (sortOrder.equals("asc")) {
                bookListDTOS.sort(Comparator.comparing(BookListDTO::getReleaseTime).reversed());
            }
        }

        return bookListDTOS;
    }
}
