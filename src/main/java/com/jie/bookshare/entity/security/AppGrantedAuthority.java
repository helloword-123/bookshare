package com.jie.bookshare.entity.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppGrantedAuthority implements GrantedAuthority {
    private String authority;
}
