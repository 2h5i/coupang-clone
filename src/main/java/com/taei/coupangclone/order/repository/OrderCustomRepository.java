package com.taei.coupangclone.order.repository;

import com.taei.coupangclone.order.dto.ResponseUserOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderCustomRepository {

    Page<ResponseUserOrders> selectUserOrders(Long userId, Pageable pageable);
}
