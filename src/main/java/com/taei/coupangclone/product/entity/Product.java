package com.taei.coupangclone.product.entity;

import com.taei.coupangclone.common.entity.BaseEntity;
import com.taei.coupangclone.seller.entity.Seller;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 3000)
    private String detail;

    @Column(nullable = false, length = 500)
    private String thumbnail;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Builder
    public Product(String name, String detail, String thumbnail, Long price, Long stock,
        Seller seller) {
        this.name = name;
        this.detail = detail;
        this.thumbnail = thumbnail;
        this.price = price;
        this.stock = stock;
        this.seller = seller;
    }

    public void updateProduct(String name, String detail, String thumbnail, Long price,
        Long stock) {
        this.name = name;
        this.detail = detail;
        this.thumbnail = thumbnail;
        this.price = price;
        this.stock = stock;
    }

    public void addStock(Long quantity) {
        this.stock += quantity;
    }

    public void removeStock(Long quantity) {
        Long restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }

    public boolean isSoldOut() {
        return stock == 0;
    }
}
