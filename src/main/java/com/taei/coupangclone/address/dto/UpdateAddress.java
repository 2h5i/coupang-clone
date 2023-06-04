package com.taei.coupangclone.address.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateAddress {

    @NotBlank
    @Size(max = 100, message = "최대 100자 입니다.")
    private String userAddress;

    @NotBlank
    @Size(min = 5, max = 10, message = "최소 5자 이상, 10자 이하입니다.")
    @Pattern(regexp = "^[0-9]*$", message = "숫자만 입력하세요.")
    private String zipcode;

    @NotNull
    private boolean defaultAddress;
}