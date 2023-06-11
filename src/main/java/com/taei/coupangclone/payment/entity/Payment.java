package com.taei.coupangclone.payment.entity;

import com.taei.coupangclone.common.entity.BaseEntity;
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
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PaymentMethod payMethod;

    @Column(nullable = false, length = 20)
    private String payCompany;

    @Builder
    public Payment(Long totalPrice, PaymentMethod payMethod, String payCompany) {
        this.totalPrice = totalPrice;
        this.payMethod = payMethod;
        this.payCompany = payCompany;
    }
}
