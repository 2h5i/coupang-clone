package com.taei.coupangclone.orderitem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseUserOrderItem {

    private Long id;
    private Long count;
    private Long orderPrice;
    private Long productId;
    private String productName;

    public ResponseUserOrderItem(Long id, Long count, Long orderPrice, Long productId,
        String productName) {
        this.id = id;
        this.count = count;
        this.orderPrice = orderPrice;
        this.productId = productId;
        this.productName = productName;
    }
}
