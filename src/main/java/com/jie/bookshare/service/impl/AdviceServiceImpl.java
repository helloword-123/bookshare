package com.jie.bookshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jie.bookshare.entity.Advice;
import com.jie.bookshare.entity.AdvicePicture;
import com.jie.bookshare.entity.DriftPicture;
import com.jie.bookshare.entity.dto.AdviceDTO;
import com.jie.bookshare.mapper.AdviceMapper;
import com.jie.bookshare.mapper.AdvicePictureMapper;
import com.jie.bookshare.mapper.DriftPictureMapper;
import com.jie.bookshare.service.AdviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhaojie
 * @since 2023-01-20
 */
@Service
public class AdviceServiceImpl extends ServiceImpl<AdviceMapper, Advice> implements AdviceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdviceMapper adviceMapper;
    @Autowired
    private DriftPictureMapper driftPictureMapper;
    @Autowired
    private AdvicePictureMapper advicePictureMapper;

    /**
     * 添加建议
     *
     * @param adviceDTO
     */
    @Override
    public void add(AdviceDTO adviceDTO) {
        logger.info("add advice: {}.", adviceDTO);

        Advice advice = new Advice();
        advice.setUserId(adviceDTO.getUserId());
        advice.setContact(adviceDTO.getContact());
        advice.setContent(adviceDTO.getContent());
        advice.setStar(adviceDTO.getStar());
        advice.setStatus(0);
        adviceMapper.insert(advice);


        // 插入图片
        for (String url : adviceDTO.getFileList()) {
            DriftPicture picture = new DriftPicture();
            picture.setPictureUrl(url);
            driftPictureMapper.insert(picture);
            AdvicePicture advicePicture = new AdvicePicture();
            advicePicture.setAdviceId(advice.getId());
            advicePicture.setPictureId(picture.getId());
            advicePictureMapper.insert(advicePicture);
        }

    }
}
