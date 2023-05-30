package com.taei.coupangclone.seller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateSellerInfo {

    @NotBlank
    @Size(max = 10, message = "10자 이하여야합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "한글 혹은 영문으로 작성되어야합니다.")
    private String sellerName;

    @NotBlank
    @Size(max = 100, message = "100자 이하여야 합니다.")
    private String address;

    @NotBlank
    @Pattern(regexp = "^[0-9]*$", message = "숫자만 입력하세요.")
    private String phone;
}
