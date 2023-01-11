package com.jie.bookshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.BookCategory;
import com.jie.bookshare.entity.dto.BookCategoryCascaderDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
public interface BookCategoryService extends IService<BookCategory> {

    /**
     * 获取所有一级目录
     * @return
     */
    List<BookCategory> getTopCategories();

    /**
     * 获取一二级图书分类，以级联方式返回
     * @return
     */
    List<BookCategoryCascaderDTO> getCategoryCascader();
}
