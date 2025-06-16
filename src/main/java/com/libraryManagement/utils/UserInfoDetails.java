package com.libraryManagement.utils;

import com.libraryManagement.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {

    private final String username; // Using email as username
    private String password;
    private String authority;

    public UserInfoDetails(Member memberInfo) {
        this.username = memberInfo.getEmail(); // Email as username
        this.password = memberInfo.getPassword();
        this.authority = memberInfo.getUserRole().name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + authority));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
