package com.chokchok.chokchokfront.common.utils;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Cookie 생성, 조회, 삭제 등을 편리하게 사용하기 위한 Util 클래스입니다.
 */
@Component
public class CookieUtils {

    /**
     * 쿠키 생성
     * @param name 생성할 쿠키의 이름
     * @param value 쿠키에 들어갈 값
     * @param maxAge 쿠키 만료 시간
     * @return Cookie
     */
    public Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }

    /**
     * 쿠키 만료 시간을 설정하지 않고, 쿠키 생성
     * @param name
     * @param value
     * @return Cookie
     */
    public Cookie createCookieWithoutMaxAge(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        return cookie;
    }

    /**
     *
     * @param cookies 쿠키 목록
     * @param name 쿠키 이름
     * @return Cookie
     */
    public Cookie getCookie(Cookie[] cookies, String name) {
        if(Objects.isNull(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), name)) {
                return cookie;
            }
        }

        return null;
    }

    /**
     * 쿠키 값 가져오기, 없으면 null 리턴합니다.
     * @param cookies 쿠키 목록
     * @param name 쿠키 이름
     * @return String - cookie value
     */
    public String getValueFromCookie(Cookie[] cookies, String name) {
        if(Objects.isNull(cookies)) {
            return null;
        }

        for(Cookie cookie : cookies) {
            if(Objects.equals(cookie.getName(), name)) {
                return cookie.getValue();
            }
        }

        return null;
    }

}
