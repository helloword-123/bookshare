package com.jie.bookshare.filter;

import com.jie.bookshare.filter.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class FilterConfig {

    @Resource
    private XssFilter xssFilter;

    /**
     * 注册xxs过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean(xssFilter);
        // 设置过滤器url匹配规则,也可以在 Filter 的实现类的 doFilter 里自定义更复杂路由匹配规则
        registration.addUrlPatterns("/*");
        // 设置过滤器优先级
        registration.setOrder(10);
        return registration;
    }
}
