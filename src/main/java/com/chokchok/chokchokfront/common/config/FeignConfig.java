package com.chokchok.chokchokfront.common.config;

import com.chokchok.chokchokfront.common.utils.CookieUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.chokchok.chokchokfront.member.controller.MemberController.ACCESS_TOKEN;
import static com.chokchok.chokchokfront.member.controller.MemberController.REFRESH_TOKEN;

/**
 * FelignClient 요청 시 Authorization 헤더에 JWT 토큰을 자동으로 추가하는 설정 클래스입니다.
 */
@RequiredArgsConstructor
@Configuration
public class FeignConfig implements RequestInterceptor {

    private final CookieUtils cookieUtils;

    /**
     * 모든 Feign 요청마다 호출되는 메소드, Authorization 헤더에 access_token 또는 refresh_token을 담는 기능입니다.
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Cookie[] cookies = request.getCookies();

            if(cookies != null) {
                String accessToken = cookieUtils.getValueFromCookie(cookies, ACCESS_TOKEN);
                String refreshToken = cookieUtils.getValueFromCookie(cookies, REFRESH_TOKEN);

                if(accessToken != null) {
                    requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
                } else if(refreshToken != null) {
                    requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken);
                }
            }

        }
    }

}
