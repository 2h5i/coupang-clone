package com.taei.coupangclone.seller.entity;

import com.taei.coupangclone.common.entity.BaseEntity;
import com.taei.coupangclone.common.entity.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 50)
    private String sellerName;

    @Column(nullable = false, length = 50, unique = true)
    private String sellerNumber;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserRole userRole;

    @Builder
    public Seller(String sellerName, String password, String sellerNumber, String address,
        String phone, String email) {
        this.sellerName = sellerName;
        this.password = password;
        this.sellerNumber = sellerNumber;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.userRole = UserRole.SELLER;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateSellerInfo(String sellerName, String address, String phone) {
        this.sellerName = sellerName;
        this.address = address;
        this.phone = phone;
    }
}
