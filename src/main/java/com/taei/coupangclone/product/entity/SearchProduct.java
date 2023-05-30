package com.taei.coupangclone.product.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchProduct {

    private String name;
    private String sellerName;
    private Long minPrice;
    private Long maxPrice;

    @Builder
    public SearchProduct(String name, String sellerName, Long minPrice, Long maxPrice) {
        this.name = name;
        this.sellerName = sellerName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
