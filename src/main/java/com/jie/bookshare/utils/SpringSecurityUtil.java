package com.jie.bookshare.utils;

import com.jie.bookshare.entity.security.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {
    public static AppUserDetails getCurrentUserDetails() {
        return (AppUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }
}
