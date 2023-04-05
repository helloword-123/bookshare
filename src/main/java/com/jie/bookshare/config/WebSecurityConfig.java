package com.jie.bookshare.config;

import com.jie.bookshare.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
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
                .antMatchers("/user/wxLogin").anonymous()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/actuator/shutdown").permitAll()
                .antMatchers("/user/checkToken").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/common/test/getToken").permitAll()
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
