package com.taei.coupangclone.cart.controller;

import com.taei.coupangclone.cart.dto.CreateCart;
import com.taei.coupangclone.cart.dto.ResponseCart;
import com.taei.coupangclone.cart.service.CartService;
import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.common.security.CommonDetailsImpl;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createCart(@RequestBody CreateCart createCart,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        cartService.createCart(createCart, commonDetails.getId());
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public PageWrapper<ResponseCart> selectCarts(@PageableDefault Pageable pageable,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        return cartService.selectCarts(pageable, commonDetails.getId());
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteCart(@PathVariable Long cartId,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        cartService.deleteCart(cartId, commonDetails.getId());
    }
}
