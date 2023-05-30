package com.taei.coupangclone.product.controller;

import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.common.security.CommonDetailsImpl;
import com.taei.coupangclone.product.dto.CreateProduct;
import com.taei.coupangclone.product.dto.ResponseProduct;
import com.taei.coupangclone.product.dto.ResponseProductForSeller;
import com.taei.coupangclone.product.dto.UpdateProduct;
import com.taei.coupangclone.product.entity.SearchProduct;
import com.taei.coupangclone.product.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public void createProduct(@RequestBody @Valid CreateProduct createProductDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        productService.createProduct(createProductDto, commonDetails.getId());
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public void updateProductById(@PathVariable Long productId,
        @RequestBody UpdateProduct updateProductDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        productService.updateProductById(productId, updateProductDto, commonDetails.getId());
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public void deleteProductById(@PathVariable Long productId,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        productService.deleteProductById(productId, commonDetails.getId());
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseProduct selectProductById(@PathVariable Long productId) {

        return productService.selectProductById(productId);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public PageWrapper<ResponseProduct> selectProducts(SearchProduct searchProduct,
        @PageableDefault() Pageable pageable) {

        return productService.selectProducts(searchProduct, pageable);
    }

    @GetMapping("/{productId}/seller")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseProductForSeller selectProductByIdForSeller(@PathVariable Long productId,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        return productService.selectProductByIdForSeller(productId, commonDetails.getId());
    }

    @GetMapping("/list/seller")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public List<ResponseProductForSeller> selectProductsForSeller(
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        return productService.selectProductsForSeller(commonDetails.getId());
    }
}
