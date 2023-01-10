package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jie.bookshare.entity.BookCategory;
import com.jie.bookshare.mapper.BookCategoryMapper;
import com.jie.bookshare.service.BookCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {

    /**
     * 获取所有一级目录
     *
     * @return
     */
    @Override
    public List<BookCategory> getTopCategories() {
        LambdaQueryWrapper<BookCategory> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookCategory::getPid, 0);

        return baseMapper.selectList(con1);
    }
}
