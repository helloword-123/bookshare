package com.jie.bookshare.filter.xss;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {
    }
}
