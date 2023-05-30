package com.taei.coupangclone.product.dto;

import com.taei.coupangclone.product.entity.Product;
import lombok.Getter;

@Getter
public class ResponseProduct {

    private String name;
    private String detail;
    private String thumbnail;
    private Long price;
    private Long stock;
    private String sellerName;

    public ResponseProduct(String name, String detail, String thumbnail, Long price, Long stock,
        String sellerName) {
        this.name = name;
        this.detail = detail;
        this.thumbnail = thumbnail;
        this.price = price;
        this.stock = stock;
        this.sellerName = sellerName;
    }

    public static ResponseProduct of(Product product) {

        return new ResponseProduct(product.getName(),
            product.getDetail(), product.getThumbnail(), product.getPrice(),
            product.getStock(), product.getSeller().getSellerName());
    }
}
