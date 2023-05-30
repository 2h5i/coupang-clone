package com.taei.coupangclone.product.repository;

import com.taei.coupangclone.product.dto.ResponseProduct;
import com.taei.coupangclone.product.entity.SearchProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {

    Page<ResponseProduct> selectProductBySearchConditions(SearchProduct searchProduct,
        Pageable pageable);
}
