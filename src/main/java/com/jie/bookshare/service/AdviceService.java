package com.jie.bookshare.service;

import com.jie.bookshare.entity.Advice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jie.bookshare.entity.dto.AdviceDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-20
 */
public interface AdviceService extends IService<Advice> {

    /**
     * 添加建议
     * @param adviceDTO
     */
    void add(AdviceDTO adviceDTO);
}
