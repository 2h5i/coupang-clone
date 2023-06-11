package com.taei.coupangclone.delivery.entity;

import com.taei.coupangclone.common.entity.BaseEntity;
import com.taei.coupangclone.orderitem.entity.OrderItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 10)
    private String zipcode;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 15)
    private String receiver;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus statuts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Builder
    public Delivery(String address, String zipcode, String phone, String receiver,
        DeliveryStatus statuts, OrderItem orderItem) {
        this.address = address;
        this.zipcode = zipcode;
        this.phone = phone;
        this.receiver = receiver;
        this.statuts = statuts;
        this.orderItem = orderItem;
    }
}
