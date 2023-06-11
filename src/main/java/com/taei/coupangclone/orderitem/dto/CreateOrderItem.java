package com.taei.coupangclone.orderitem.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderItem {

    @NotNull
    private Long productId;

    @NotNull
    private Long sellerId;

    @NotNull
    private Long count;

    @NotNull
    private Long orderPrice;
}
