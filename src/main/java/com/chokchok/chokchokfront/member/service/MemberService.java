package com.chokchok.chokchokfront.member.service;

import com.chokchok.chokchokfront.common.dto.ResponseDto;
import com.chokchok.chokchokfront.common.utils.CookieUtils;
import com.chokchok.chokchokfront.member.client.MemberClient;
import com.chokchok.chokchokfront.member.dto.MemberLoginRequestDto;
import com.chokchok.chokchokfront.member.dto.MemberRegisterRequestDto;
import com.chokchok.chokchokfront.member.dto.MemberRegisterResponseDto;
import com.chokchok.chokchokfront.member.dto.TokenResponseDto;
import com.chokchok.chokchokfront.member.exception.LoginFailedException;
import com.chokchok.chokchokfront.member.exception.LogoutFailedException;
import com.chokchok.chokchokfront.member.exception.MemberRegisterFailedException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 로그인, 회원 등록, 수정, 삭제를 위한 Service 클래스입니다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberClient memberClient;

    /**
     * 로그인
     * @param loginRequestDto
     * @return TokenResponseDto
     */
    public TokenResponseDto login(MemberLoginRequestDto loginRequestDto) {
        try {

            ResponseDto<TokenResponseDto> response = memberClient.login(loginRequestDto);
            return response.getData();

        } catch (Exception e) {
            throw new LoginFailedException("로그인 실패");
        }
    }

    /**
     * 로그아웃
     */
    public void logout() {
        try {
            ResponseDto<Void> response = memberClient.logout();
        } catch (Exception e) {
            throw new LogoutFailedException("로그아웃 실패");
        }
    }

    /**
     * 회원 가입
     * @param registerRequestDto
     * @return MemberRegisterResponseDto
     */
    public MemberRegisterResponseDto register(MemberRegisterRequestDto registerRequestDto) {
        if(!registerRequestDto.getPassword().equals(registerRequestDto.getPasswordConfirm())) {
            throw new MemberRegisterFailedException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        try {

            ResponseDto<MemberRegisterResponseDto> response = memberClient.register(registerRequestDto);
            return response.getData();

        } catch (Exception e) {
            throw new MemberRegisterFailedException("회원가입 실패");
        }

    }
}
