package com.jie.bookshare.filter;

import com.jie.bookshare.filter.token.TokenFilter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@SpringBootConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, order = Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityFilterConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private TokenFilter tokenFilter;

    /**
     * 设置接口拦截规则
     *
     * @param httpSecurity httpSecurity对象
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/shutdown").permitAll()
                .antMatchers("/user/wxLogin").anonymous()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/user/checkToken").permitAll()

                .antMatchers("/common/test").permitAll()
                .antMatchers("/common/test/getToken").permitAll()

                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/doc.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors();
    }
}
