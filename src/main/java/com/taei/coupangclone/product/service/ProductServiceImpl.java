package com.taei.coupangclone.product.service;

import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.product.dto.CreateProduct;
import com.taei.coupangclone.product.dto.ResponseProduct;
import com.taei.coupangclone.product.dto.ResponseProductForSeller;
import com.taei.coupangclone.product.dto.UpdateProduct;
import com.taei.coupangclone.product.entity.Product;
import com.taei.coupangclone.product.entity.SearchProduct;
import com.taei.coupangclone.product.repository.ProductRepository;
import com.taei.coupangclone.seller.entity.Seller;
import com.taei.coupangclone.seller.repository.SellerRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public void createProduct(CreateProduct createProductDto, Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
            () -> new IllegalArgumentException("판매자가 존재하지 않습니다.")
        );

        Product product = Product.builder()
            .name(createProductDto.getName())
            .detail(createProductDto.getDetail())
            .thumbnail(createProductDto.getThumbnail())
            .price(createProductDto.getPrice())
            .stock(createProductDto.getStock())
            .seller(seller)
            .build();

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductById(Long productId, UpdateProduct updateProductDto,
        Long sellerId) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new IllegalArgumentException("수정할 상품이 존재하지 않습니다.")
        );

        if (!Objects.equals(product.getSeller().getId(), sellerId)) {
            throw new IllegalArgumentException("해당 상품에 대한 수정 권한이 없습니다.");
        }

        product.updateProduct(updateProductDto.getName(),
            updateProductDto.getDetail(),
            updateProductDto.getThumbnail(),
            updateProductDto.getPrice(),
            updateProductDto.getStock());

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductById(Long productId, Long sellerId) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new IllegalArgumentException("삭제할 상품이 존재하지 않습니다.")
        );

        if (!Objects.equals(product.getSeller().getId(), sellerId)) {
            throw new IllegalArgumentException("해당 상품에 대한 삭제 권한이 없습니다.");
        }

        productRepository.delete(product);
    }

    @Override
    public ResponseProduct selectProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new IllegalArgumentException("조회할 상품이 존재하지 않습니다.")
        );

        return ResponseProduct.of(product);
    }

    @Override
    public PageWrapper<ResponseProduct> selectProducts(SearchProduct searchProduct,
        Pageable pageable) {
        Page<ResponseProduct> products = productRepository.selectProductBySearchConditions(
            searchProduct, pageable);

        return PageWrapper.of(products);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseProductForSeller selectProductByIdForSeller(Long productId, Long sellerId) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new IllegalArgumentException("조회할 상품이 존재하지 않습니다.")
        );

        if (!Objects.equals(product.getSeller().getId(), sellerId)) {
            throw new IllegalArgumentException("해당 상품에 대한 조회 권한이 없습니다.");
        }

        return ResponseProductForSeller.of(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseProductForSeller> selectProductsForSeller(Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);

        return ResponseProductForSeller.of(products);
    }
}
