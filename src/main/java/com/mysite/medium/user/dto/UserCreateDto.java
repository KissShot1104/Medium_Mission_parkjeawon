package com.mysite.medium.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
public class UserCreateDto {
    @Size(min = 3, max = 25)
    @NotBlank(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotBlank(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
}