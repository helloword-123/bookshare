package com.jie.bookshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jie.bookshare.entity.BookCategory;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhaojie
 * @since 2022-12-16
 */
public interface BookCategoryMapper extends BaseMapper<BookCategory> {

    /**
     * 先查一级目录的所有子目录
     * @param pid
     * @return
     */
    List<Integer> selectSubCategoryIds(Integer pid);
}
