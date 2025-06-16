package com.libraryManagement.utils;

import com.libraryManagement.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUtil {

    private SecretKey key;

    private Set<String> blacklistedTokens = new HashSet<>();

    public void invalidateToken(String token) {
        blacklistedTokens.add(token);
    }

    @PostConstruct
    public void init() {
        String SECRET = "Sy+K9Vlqun/cv0hqCZsh5lZeGdEGzEaLD497KhJyBhQ="; // Replace with a real secret key
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email, String role) {
        long EXPIRATION = 1000 * 60 * 60 * 10L; // 10 hours
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public UserRole extractRole(String token) {
        String roleStr = extractAllClaims(token).get("role", String.class);
        return UserRole.valueOf(roleStr);
    }

    public boolean validateToken(String token) {
        if (blacklistedTokens.contains(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
