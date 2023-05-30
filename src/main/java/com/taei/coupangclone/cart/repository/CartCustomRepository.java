package com.taei.coupangclone.cart.repository;

import com.taei.coupangclone.cart.dto.ResponseCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartCustomRepository {

    Page<ResponseCart> selectProductByUserId(Pageable pageable, Long userId);
}
