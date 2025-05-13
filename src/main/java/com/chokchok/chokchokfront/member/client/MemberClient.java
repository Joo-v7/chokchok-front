package com.chokchok.chokchokfront.member.client;

import com.chokchok.chokchokfront.common.config.FeignConfig;
import com.chokchok.chokchokfront.common.dto.ResponseDto;
import com.chokchok.chokchokfront.member.dto.MemberLoginRequestDto;
import com.chokchok.chokchokfront.member.dto.MemberRegisterRequestDto;
import com.chokchok.chokchokfront.member.dto.MemberRegisterResponseDto;
import com.chokchok.chokchokfront.member.dto.TokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 로그인, 회원 등록/수정/삭제 관련 Feign Client
 */
@FeignClient(name = "GATEWAY", contextId = "MemberClient" ,configuration = FeignConfig.class)
public interface MemberClient {

    /**
     * Auth 서버로 로그인 요청
     * @param memberLoginRequestDto
     * @return ResponseDto<TokenResponseDto>
     */
    @PostMapping("/auth/login")
    ResponseDto<TokenResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto);

    @PostMapping("/auth/logout")
    ResponseDto<Void> logout();

    /**
     * chokchok-api 서버로 회원가입 요청
     * @param memberRegisterRequestDto
     * @return ResponseDto<MemberRegisterResponseDto>
     */
    @PostMapping("/api/members")
    ResponseDto<MemberRegisterResponseDto> register(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto);
}
