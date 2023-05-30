package com.taei.coupangclone.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignUp {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0-9 만 입력하세요.")
    private String password;

    @NotBlank
    @Size(max = 10, message = "10자 이하여야합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "한글 혹은 영문으로 작성되어야합니다.")
    private String userName;

    @NotBlank
    @Pattern(regexp = "^[0-9]*$", message = "숫자만 입력하세요.")
    private String phone;
}
