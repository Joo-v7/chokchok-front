package com.chokchok.chokchokfront.common.client;

import com.chokchok.chokchokfront.common.FeignClientException;
import com.chokchok.chokchokfront.common.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Feign의 기본 에러 응답을 커스텀 하기 위한 ErrorDecoder 구현 클래스
 * Feign Client에서 외부 API 호출 시 에러가 발생하면, 해당 응답을 파싱하여 커스텀 예외(FeignClientException)로 변환합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    /**
     * FeignClient 호출 중 에러 응답을 받아 커스텀 예외로 변환하는 메서드
     *
     * @param s
     * @param response
     * @return Exception
     */
    @Override
    public Exception decode(String s, Response response) {
        try {
            if(response.body() == null) {
                log.error("Feign error occurred: Empty body");
                throw new FeignClientException("Feign response body is null");
            }

            ErrorResponseDto errorResponseDto = objectMapper.readValue(response.body().asInputStream(), ErrorResponseDto.class);
            log.error("=== Feign error occurred  === time:{}, code:{}, message:{}", errorResponseDto.timestamp(), errorResponseDto.errorCode(), errorResponseDto.message());

            return new FeignClientException(errorResponseDto.message());

        } catch(Exception e) {
            log.error("Feign error occurred: response failed");
            return new FeignClientException("Feign response failed");
        }
    }
}
