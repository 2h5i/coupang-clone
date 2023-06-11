package com.taei.coupangclone.orderitem.entity;

import com.taei.coupangclone.common.entity.BaseEntity;
import com.taei.coupangclone.order.entity.Order;
import com.taei.coupangclone.product.entity.Product;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long count; // 주문 수량

    @Column(nullable = false)
    private Long orderPrice; // 주문 가격

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem(Product product, Long count, Long orderPrice) {
        this.product = product;
        this.count = count;
        this.orderPrice = orderPrice;
        this.status = OrderItemStatus.ORDER;
    }

    // 생성 메서드
    public static OrderItem createOrderItem(Product product, Long count, Long orderPrice) {
        OrderItem orderItem = new OrderItem(product, count, orderPrice);

        product.removeStock(count);
        return orderItem;
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        this.product.addStock(this.count);
    }

    public Long getTotalPrice() {
        return this.count * this.orderPrice;
    }
}
