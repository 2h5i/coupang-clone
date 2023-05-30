package com.taei.coupangclone.product.service;

import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.product.dto.CreateProduct;
import com.taei.coupangclone.product.dto.ResponseProduct;
import com.taei.coupangclone.product.dto.ResponseProductForSeller;
import com.taei.coupangclone.product.dto.UpdateProduct;
import com.taei.coupangclone.product.entity.SearchProduct;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    void createProduct(CreateProduct createProductDto, Long sellerId);

    void updateProductById(Long productId, UpdateProduct updateProductDto, Long sellerId);

    void deleteProductById(Long productId, Long sellerId);

    ResponseProduct selectProductById(Long productId);

    PageWrapper<ResponseProduct> selectProducts(SearchProduct searchProduct, Pageable pageable);

    ResponseProductForSeller selectProductByIdForSeller(Long productId, Long sellerId);

    List<ResponseProductForSeller> selectProductsForSeller(Long sellerId);
}
