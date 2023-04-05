package com.jie.bookshare.filter.xss;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XSSFilterConfig {

    /**
     * 注册xxs过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new XssFilter());
        // 设置过滤器url匹配规则,也可以在 Filter 的实现类的 doFilter 里自定义更复杂路由匹配规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 设置过滤器名称
        filterRegistrationBean.setName("XssFilter");
        // 设置过滤器优先级
        filterRegistrationBean.setOrder(99);
        return filterRegistrationBean;
    }
}

