package com.taei.coupangclone.product.repository;

import com.taei.coupangclone.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>,
    ProductCustomRepository {

    List<Product> findBySellerId(Long sellerId);
}
