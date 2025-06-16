package com.libraryManagement.security;

import com.libraryManagement.enums.UserRole;
import com.libraryManagement.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Skip authentication check for public endpoints
        if (requestURI.equals("/api/auth/register")
                || requestURI.equals("/api/auth/login")
                || requestURI.equals("/api/auth/welcome")
                || requestURI.startsWith("/api/books/search")
                || requestURI.startsWith("/api/books/getbooks")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtFromRequest(request);

        if (token != null && jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            UserRole role = jwtUtil.extractRole(token);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    Collections.singletonList(authority)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid or expired");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
