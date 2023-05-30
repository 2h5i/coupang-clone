package com.taei.coupangclone.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateProduct {

    @NotBlank
    @Size(max = 50, message = "50자 이하여야 합니다.")
    private String name;

    @NotBlank
    @Size(max = 3000, message = "3000자 이하여야 합니다.")
    private String detail;

    @NotBlank
    @Size(max = 3000, message = "500자 이하여야 합니다.")
    private String thumbnail;

    @NotNull
    private Long price;

    @NotNull
    private Long stock;
}
