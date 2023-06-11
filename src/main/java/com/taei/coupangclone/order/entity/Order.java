package com.taei.coupangclone.order.entity;

import com.taei.coupangclone.common.entity.BaseEntity;
import com.taei.coupangclone.orderitem.entity.OrderItem;
import com.taei.coupangclone.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    // 생성 메서드
    public static Order createOrder(User user, List<OrderItem> orderItems) {
        Order order = new Order();
        order.updateUser(user);
        Long total = 0L;

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
            total += orderItem.getOrderPrice();
        }

        order.updateTotalPrice(total);

        return order;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.addOrder(this);
    }
}
