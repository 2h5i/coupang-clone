package com.taei.coupangclone.seller.dto;

import com.taei.coupangclone.seller.entity.Seller;
import lombok.Getter;

@Getter
public class ResponseSellerForProduct {

    private String sellerName;

    private ResponseSellerForProduct(Seller seller) {
        this.sellerName = seller.getSellerName();
    }

    public static ResponseSellerForProduct of(Seller seller) {
        
        return new ResponseSellerForProduct(seller);
    }
}
