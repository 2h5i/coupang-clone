package com.taei.coupangclone.cart.dto;

import lombok.Getter;

@Getter
public class ResponseCart {

    private Long id;
    private Long productId;
    private String name;
    private Long price;
    private String sellerName;

    public ResponseCart(Long id, Long productId, String name, Long price, String sellerName) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.sellerName = sellerName;
    }
}
