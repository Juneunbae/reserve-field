package com.example.reservefield.common.security;

import com.example.reservefield.domain.user.User;
import com.example.reservefield.dto.response.TokenInfoDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.access-expire-time}")
    private Long accessExpireTime;

    @Value("${jwt.refresh-expire-time}")
    private Long refreshExpireTime;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.refresh}")
    private String refreshKey;

    public String accessToken(User user) {
        byte[] accessSecret = this.secretKey.getBytes(StandardCharsets.UTF_8);

        return Jwts.builder()
            .claim("id", user.getId())
            .claim("role", user.getRole())
            .claim("type", JwtType.ACCESS)
            .issuedAt(new Date())
            .expiration(new Date(new Date().getTime() + this.accessExpireTime))
            .signWith(Keys.hmacShaKeyFor(accessSecret))
            .compact();
    }

    public String refreshToken(User user) {
        byte[] refreshSecret = this.refreshKey.getBytes(StandardCharsets.UTF_8);

        return Jwts.builder()
            .claim("id", user.getId())
            .claim("role", user.getRole())
            .claim("type", JwtType.REFRESH)
            .issuedAt(new Date())
            .expiration(new Date(new Date().getTime() + this.refreshExpireTime))
            .signWith(Keys.hmacShaKeyFor(refreshSecret))
            .compact();
    }

    public TokenInfoDto generateToken(User user) {
        return new TokenInfoDto(
            "Bearer",
            this.accessToken(user),
            this.refreshToken(user)
        );
    }
}