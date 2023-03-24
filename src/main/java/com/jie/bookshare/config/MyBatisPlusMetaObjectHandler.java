package com.jie.bookshare.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MyMetaObjectHandler
 * @Description mybatis-plus的自动填充实体类的属性
 * @Author wuhaojie
 * @Date 2022/3/19 23:32
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动注入对象值
     *
     * @param metaObject 数据表字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 属性名称，不是字段
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 更新时字段注入
     *
     * @param metaObject 数据表字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
