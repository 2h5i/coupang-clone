package com.taei.coupangclone.seller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SellerMyPage {

    private Long id;
    private String email;
    private String sellerName;
    private String sellerNumber;
    private String address;
    private String phone;
}
