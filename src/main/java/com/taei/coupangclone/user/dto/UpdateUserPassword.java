package com.taei.coupangclone.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserPassword {

    @NotBlank
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0-9 만 입력하세요.")
    private String oldPassword;

    @NotBlank
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0-9 만 입력하세요.")
    private String newPassword;
}
