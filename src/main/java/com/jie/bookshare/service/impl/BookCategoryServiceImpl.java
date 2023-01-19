package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.BookCategory;
import com.jie.bookshare.entity.dto.BookCategoryCascaderDTO;
import com.jie.bookshare.mapper.BookCategoryMapper;
import com.jie.bookshare.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {

    @Autowired
    private BookCategoryMapper bookCategoryMapper;

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

    /**
     * 获取一二级图书分类，以级联方式返回
     *
     * @return
     */
    @Override
    public List<BookCategoryCascaderDTO> getCategoryCascader() {
        List<BookCategoryCascaderDTO> res = new ArrayList<>();
        // 查询所有一级目录
        LambdaQueryWrapper<BookCategory> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookCategory::getPid, 0);
        List<BookCategory> topCategories = bookCategoryMapper.selectList(con1);

        for (BookCategory father : topCategories) {
            BookCategoryCascaderDTO bccDTO = new BookCategoryCascaderDTO();
            bccDTO.setId(father.getId());
            bccDTO.setName(father.getName());

            // 查询子目录
            LambdaQueryWrapper<BookCategory> con2 = new LambdaQueryWrapper<>();
            con2.eq(BookCategory::getPid, father.getId());
            List<BookCategory> subCategories = bookCategoryMapper.selectList(con2);

            List<Map<String, Object>> children = new ArrayList<>();
            for (BookCategory subCategory : subCategories) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", subCategory.getId());
                map.put("name", subCategory.getName());
                children.add(map);
            }

            bccDTO.setChildren(children);

            res.add(bccDTO);
        }

        return res;
    }

    /**
     * 根据id获取分类全名（包括父分类，以"/"分隔）
     *
     * @param categoryId
     * @return
     */
    @Override
    public String getCategoryFullName(Integer categoryId) {
        BookCategory subCategory = bookCategoryMapper.selectById(categoryId);
        LambdaQueryWrapper<BookCategory> con1 = new LambdaQueryWrapper<>();
        con1.eq(BookCategory::getId, subCategory.getPid());
        BookCategory topCategory = bookCategoryMapper.selectOne(con1);

        StringBuilder fullName = new StringBuilder();
        fullName.append(topCategory.getName()).append(" / ").append(subCategory.getName());

        return fullName.toString();
    }
}
