package com.chokchok.chokchokfront.member.controller;

import com.chokchok.chokchokfront.common.utils.CookieUtils;
import com.chokchok.chokchokfront.member.dto.MemberLoginRequestDto;
import com.chokchok.chokchokfront.member.dto.MemberRegisterRequestDto;
import com.chokchok.chokchokfront.member.dto.MemberRegisterResponseDto;
import com.chokchok.chokchokfront.member.dto.TokenResponseDto;
import com.chokchok.chokchokfront.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 로그인, 회원 등록/수정/삭제 등의 회원 관련 컨트롤러 클래스입니다.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final CookieUtils cookieUtils;

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 30 * 24 * 60 * 60; // 30일


    /**
     * 로그인 폼을 리턴합니다.
     * @return String - 로그인 폼 화면
     */
    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(ACCESS_TOKEN.equals(cookie.getName()) || REFRESH_TOKEN.equals(cookie.getName())) {
                    return "redirect:/";
                }
            }
        }

        return "/member/login";
    }

    /**
     * 로그인 POST 요청을 받으면 Auth 서버로 JWT 토큰 발급 후 쿠키로 등록합니다
     * @param memberLoginRequestDto
     * @param response
     * @return String - 홈 페이지
     */
    @PostMapping("/login")
    public String login(
            @ModelAttribute @Valid MemberLoginRequestDto memberLoginRequestDto,
            HttpServletResponse response) {
        TokenResponseDto responseDto = memberService.login(memberLoginRequestDto);

        Cookie accessTokenCookie = cookieUtils.createCookie(ACCESS_TOKEN, responseDto.getAccessToken(), responseDto.getExpiresIn().intValue());
        Cookie refreshTokenCookie = cookieUtils.createCookie(REFRESH_TOKEN, responseDto.getRefreshToken(), REFRESH_TOKEN_EXPIRATION_TIME);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        memberService.logout();

        Cookie[] cookies = request.getCookies();
        Cookie accessTokenCookie = cookieUtils.getCookie(cookies, ACCESS_TOKEN);
        Cookie refreshTokenCookie = cookieUtils.getCookie(cookies, REFRESH_TOKEN);

        if(accessTokenCookie != null) {
            accessTokenCookie.setMaxAge(0);
            accessTokenCookie.setPath("/");
            response.addCookie(accessTokenCookie);
        }

        if(refreshTokenCookie != null) {
            refreshTokenCookie.setMaxAge(0);
            refreshTokenCookie.setPath("/");
            response.addCookie(refreshTokenCookie);
        }

        return "redirect:/";
    }

    /**
     * 회원가입 폼을 리턴합니다.
     * @return - String
     */
    @GetMapping("/register")
    public String register() {
        return "/member/register";
    }

    /**
     * 회원가입 POST 요청을 받으면 회원을 등록합니다.
     */
    @PostMapping("/register")
    public String register(
            @ModelAttribute @Valid MemberRegisterRequestDto memberRegisterRequestDto) {
        MemberRegisterResponseDto responseDto = memberService.register(memberRegisterRequestDto);
        return "redirect:/members/login";
    }

}
