package com.chokchok.chokchokfront.common.filter;

import com.chokchok.chokchokfront.common.client.AuthClient;
import com.chokchok.chokchokfront.common.utils.CookieUtils;
import com.chokchok.chokchokfront.member.dto.TokenResponseDto;
import feign.FeignException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.chokchok.chokchokfront.member.controller.MemberController.ACCESS_TOKEN;
import static com.chokchok.chokchokfront.member.controller.MemberController.REFRESH_TOKEN;

/**
 * 모든 요청에 대해서 jwt 쿠키 정보들을 확인합니다.
 * access token이 만료되고 refresh token만 있으면, JWT 토큰을 재발급하는 필터입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final CookieUtils cookieUtils;
    private final AuthClient authClient;

    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 30 * 24 * 60 * 60; // 30일

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpRequest.getCookies();
        String accessToken = cookieUtils.getValueFromCookie(cookies, ACCESS_TOKEN);
        String refreshToken = cookieUtils.getValueFromCookie(cookies, REFRESH_TOKEN);

        if (accessToken == null && refreshToken != null) {
            try {
                TokenResponseDto responseDto = authClient.reissue().getData();

                Cookie accessTokenCookie = cookieUtils.createCookie(ACCESS_TOKEN, responseDto.getAccessToken(), responseDto.getExpiresIn().intValue());
                Cookie refreshTokenCookie = cookieUtils.createCookie(REFRESH_TOKEN, responseDto.getRefreshToken(), REFRESH_TOKEN_EXPIRATION_TIME);

                httpResponse.addCookie(accessTokenCookie);
                httpResponse.addCookie(refreshTokenCookie);
            } catch (FeignException e) {
                log.error("JWT 재발급 실패", e);
                httpResponse.sendRedirect("/members/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
