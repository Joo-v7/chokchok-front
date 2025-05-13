package com.chokchok.chokchokfront.common.advice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import static com.chokchok.chokchokfront.member.controller.MemberController.ACCESS_TOKEN;
import static com.chokchok.chokchokfront.member.controller.MemberController.REFRESH_TOKEN;

/**
 * 전역적으로 모든 컨트롤러에 공통된 모델 속성을 주입하는 클래스입니다.
 * 헤더나 공통 레이아웃에서 필요한 데이터를 제공합니다.
 */
@ControllerAdvice
public class WebControllerAdvice {

    /**
     * access_token or refresh_token이 존재하면 "로그인 상태"로 간주한다.
     * @param request
     * @return boolean
     */
    @ModelAttribute("isLogin")
    public boolean addIsLoginAttribute(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ACCESS_TOKEN) || cookie.getName().equals(REFRESH_TOKEN)) {
                    return true;
                }
            }
        }

        return false;
    }
}
