package com.libraryManagement.security;

import com.libraryManagement.service.UserInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public AuthenticationProvider authenticationProvider(UserInfoService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder()); // Using built-in encryption
        return provider;
    }
}
