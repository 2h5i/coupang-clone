package com.taei.coupangclone.order.repository;

import com.taei.coupangclone.order.dto.ResponseUserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderCustomRepository {

    Page<ResponseUserOrder> selectUserOrder(Long userId, Pageable pageable);
}
