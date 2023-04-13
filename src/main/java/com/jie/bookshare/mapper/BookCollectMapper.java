package com.jie.bookshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jie.bookshare.entity.BookCollect;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhaojie
 * @since 2023-02-14
 */
@Component
public interface BookCollectMapper extends BaseMapper<BookCollect> {

    List<Integer> getCollectedBooks(Integer userId);
}
