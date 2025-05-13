package com.chokchok.chokchokfront.member.dto;

import java.time.LocalDate;

/**
 * 회원 등록 이후 클라이언트에게 반환하는 멤버 정보를 담은 response DTO
 * @param id
 * @param username
 * @param email
 * @param dateOfBirth
 * @param gender
 * @param status
 * @param memberGrade
 * @param memberRole
 */
public record MemberRegisterResponseDto(
        Long id,
        String username,
        String email,
        LocalDate dateOfBirth,
        String gender,
        String status,
        String memberGrade,
        String memberRole
) {
}
