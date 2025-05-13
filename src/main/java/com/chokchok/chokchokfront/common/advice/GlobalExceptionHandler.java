package com.chokchok.chokchokfront.common.advice;

import com.chokchok.chokchokfront.member.exception.LoginFailedException;
import com.chokchok.chokchokfront.member.exception.MemberRegisterFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;

/**
 * 예외 처리를 위한 Controller Advice 입니다.
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 존재하지 않는 url 요청 예외 처리 핸들러
     * @param e NoResourceFoundException
     * @param response HttpServletResponse
     * @throws IOException
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public void handleNoHandlerFoundException(NoResourceFoundException e, HttpServletResponse response) throws IOException {
        log.info("존재하지 않는 API 요청: {}", e.getMessage());
        response.sendRedirect("/");
    }

    /**
     * 유효성 검사 실패 예외 처리 핸들러
     * @param e MethodArgumentNotValidException
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        // 에러가 발생한 해당 페이지로 리다이렉트
        response.sendRedirect(request.getHeader("Referer"));
    }

    /**
     * 로그인 실패 예외 처리 핸들러
     * @param e LoginFailedException
     * @param response HttpServletResponse
     * @throws IOException
     */
    @ExceptionHandler(LoginFailedException.class)
    public void handleLoginFailedException(LoginFailedException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendRedirect("/members/login");
    }

    /**
     * 회원 가입 예외 처리 핸들러
     * @param e MemberRegisterFailedException
     * @param response HttpServletResponse
     * @throws IOException
     */
    @ExceptionHandler(MemberRegisterFailedException.class)
    public void handleMemberRegisterFailedException(MemberRegisterFailedException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendRedirect("/members/register");
    }

}
