package com.taei.coupangclone.product.dto;

import com.taei.coupangclone.product.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ResponseProductForSeller {

    private String name;
    private String detail;
    private String thumbnail;
    private Long productPrice;
    private Long stock;

    private ResponseProductForSeller(Product product) {
        this.name = product.getName();
        this.detail = product.getDetail();
        this.thumbnail = product.getThumbnail();
        this.productPrice = product.getPrice();
        this.stock = product.getStock();
    }

    public static ResponseProductForSeller of(Product product) {

        return new ResponseProductForSeller(product);
    }

    public static List<ResponseProductForSeller> of(List<Product> products) {
        
        return products.stream().map(ResponseProductForSeller::of).collect(Collectors.toList());
    }
}
