package com.taei.coupangclone.order.service;

import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.order.dto.CreateOrder;
import com.taei.coupangclone.order.dto.ResponseUserOrder;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    void createOrder(CreateOrder createOrder, Long userId);

    PageWrapper<ResponseUserOrder> selectUserOrder(Long userId, Pageable pageable);
}
