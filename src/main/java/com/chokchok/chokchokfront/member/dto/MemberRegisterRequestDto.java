package com.chokchok.chokchokfront.member.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
        @NotBlank(message = "사용자 이름은 필수 입력 사항입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}$", message = "사용자 이름은 한글 또는 영어 2자 이상 20자 이하로 입력해야 합니다.")
        private String username;

        @NotBlank(message = "이메일은 필수 입력 사항입니다.")
        @Size(max = 100, message = "이메일은 100자까지 입력 가능합니다.")
        @Email(message = "올바른 이메일 양식이 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
        private String password;

        @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
        private String passwordConfirm;

        @NotNull(message = "생년월일은 필수 입력 사항입니다.")
        private LocalDate dateOfBirth;

        @NotNull(message = "성별은 필수 입력 사항입니다.")
        private String gender;
}
