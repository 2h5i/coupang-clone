package com.taei.coupangclone.cart.service;

import com.taei.coupangclone.cart.dto.CreateCart;
import com.taei.coupangclone.cart.dto.ResponseCart;
import com.taei.coupangclone.common.core.PageWrapper;
import org.springframework.data.domain.Pageable;

public interface CartService {

    void createCart(CreateCart createCart, Long userId);

    void deleteCart(Long cartId, Long userId);

    PageWrapper<ResponseCart> selectCarts(Pageable pageable, Long userId);
}
