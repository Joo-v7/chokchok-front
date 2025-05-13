package com.chokchok.chokchokfront.common.client;

import com.chokchok.chokchokfront.common.config.FeignConfig;
import com.chokchok.chokchokfront.common.dto.ResponseDto;
import com.chokchok.chokchokfront.member.dto.TokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Auth 서버와 통신하기 위한 Feign Client
 */
@FeignClient(name = "GATEWAY", contextId="AuthClient", configuration = FeignConfig.class)
public interface AuthClient {

    /**
     * Auth 서버로 jwt access token 재발급 요청
     */
    @PostMapping("/auth/reissue")
    ResponseDto<TokenResponseDto> reissue();
}
