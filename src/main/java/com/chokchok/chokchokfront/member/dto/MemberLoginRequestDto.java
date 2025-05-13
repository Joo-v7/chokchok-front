package com.chokchok.chokchokfront.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 로그인 요청을 위한 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequestDto {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String id;

    @NotBlank
    private String password;

}
