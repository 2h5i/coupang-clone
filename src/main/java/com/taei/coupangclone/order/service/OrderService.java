package com.taei.coupangclone.order.service;

import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.order.dto.CreateOrder;
import com.taei.coupangclone.order.dto.ResponseUserOrders;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    void createOrder(CreateOrder createOrder, Long userId);

    PageWrapper<ResponseUserOrders> selectUserOrders(Long userId, Pageable pageable);
}
