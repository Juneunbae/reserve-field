package com.example.reservefield.common.security;

import com.example.reservefield.domain.user.UserDetailService;
import com.example.reservefield.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final UserDetailService userDetailService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final String ROLE = "role";
    private final String TYPE = "type";

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(ROLE) == null || claims.get(TYPE) == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다.");
        }

        Long userId = Long.valueOf(String.valueOf(claims.get("id")));
        log.info("getAuthentication : {}", userId);

        UserDetails principal = userDetailService.loadUserById(userId);
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    public Claims parseClaims(String accessToken) {
        byte[] accessSecret = secretKey.getBytes(StandardCharsets.UTF_8);

        try {
            return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(accessSecret))
                .build()
                .parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 정보입니다.");
        }
    }

    public boolean validateToken(String token) {
        log.info("Start validateToken");
        byte[] accessSecret = secretKey.getBytes(StandardCharsets.UTF_8);

        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(accessSecret))
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("유효하지 않은 토큰 정보입니다.", e);
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰 정보입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims 부분이 비어있습니다.", e);
        }

        return false;
    }
}