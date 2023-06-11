package com.taei.coupangclone.order.dto;

import com.taei.coupangclone.orderitem.dto.ResponseUserOrderItem;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseUserOrders {

    private Long id;
    private Long totalPrice;
    private List<ResponseUserOrderItem> userOrderItems = new ArrayList<>();

    public ResponseUserOrders(Long id, Long totalPrice,
        List<ResponseUserOrderItem> userOrderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.userOrderItems = userOrderItems;
    }
}
