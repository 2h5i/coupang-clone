package com.taei.coupangclone.order.controller;

import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.common.security.CommonDetailsImpl;
import com.taei.coupangclone.order.dto.CreateOrder;
import com.taei.coupangclone.order.dto.ResponseUserOrders;
import com.taei.coupangclone.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createOrder(@RequestBody CreateOrder createOrder,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        orderService.createOrder(createOrder, commonDetails.getId());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public PageWrapper<ResponseUserOrders> selectUserOrders(
        @AuthenticationPrincipal CommonDetailsImpl commonDetails,
        @PageableDefault() Pageable pageable) {

        return orderService.selectUserOrders(commonDetails.getId(), pageable);
    }
}
