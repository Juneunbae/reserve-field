package com.example.reservefield.common.security;

import com.example.reservefield.common.PathList;
import com.example.reservefield.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    // Request Header 에서 JWT 토큰 추출하기
    public String resolveToken(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
                return bearerToken.substring(7);
            }

            return null;
        } catch (Exception e) {
            log.error("Authorization 및 Bearer 추출 에러");
            throw new CustomException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 정보입니다.");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        if (pathMatcher.match(requestURI, "/error")) {
            return;
        }
        log.info(requestURI);

        // ADMIN || EMPLOYEE 경로에 해당하지 않을 경우
        if (!isMatch(requestURI)) {
            log.info("URI: {}, 권한 검증이 필요 없는 URI", requestURI);
            chain.doFilter(request, response);
            return;
        }

        // Request Header 에서 JWT 토큰 추출하기
        log.info("JWT TOKEN 추출");
        String token = resolveToken(httpServletRequest);

        // validateToken 으로 토큰 유효성 검사
        if (token != null && jwtProvider.validateToken(token)) {
            log.info("{}", jwtProvider.validateToken(token));

            Authentication authentication = jwtProvider.getAuthentication(token);
            log.info("doFilter - {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } else {
            // 토큰이 없거나 유효하지 않은 경우 403 상태 코드와 메시지를 반환
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            log.error("유효한 토큰 정보가 이닙니다.");
            httpResponse.getWriter().write("접근이 허용되지 않습니다. 유효한 토큰을 제공하십시오.");
            httpResponse.getWriter().flush(); // 응답이 반환되었으므로 체인 처리를 중단합니다.
        }
    }

    public Boolean isMatch(String requestURI) {
        for (String path : PathList.PLAYER_LIST) {
            if (pathMatcher.match(path, requestURI)) {
                log.info("PLAYER_LIST 내 일치하는 URI 존재");
                return true;
            }
        }

        for (String path : PathList.ADMIN_LIST) {
            if (pathMatcher.match(path, requestURI)) {
                log.info("ADMIN_LIST 내 일치하는 URI 존재");
                return true;
            }
        }

        return false;
    }
}