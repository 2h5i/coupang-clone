package com.taei.coupangclone.address.entity;

import com.taei.coupangclone.user.entity.User;
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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String userAddress;

    @Column(nullable = false, length = 10)
    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Address(String userAddress, String zipcode, User user) {
        this.userAddress = userAddress;
        this.zipcode = zipcode;
        this.user = user;
    }

    public void updateAddressInfo(String userAddress, String zipcode) {
        this.userAddress = userAddress;
        this.zipcode = zipcode;
    }
}
