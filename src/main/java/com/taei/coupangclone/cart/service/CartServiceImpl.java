package com.taei.coupangclone.cart.service;

import com.taei.coupangclone.cart.dto.CreateCart;
import com.taei.coupangclone.cart.dto.ResponseCart;
import com.taei.coupangclone.cart.entity.Cart;
import com.taei.coupangclone.cart.repository.CartRepository;
import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.product.entity.Product;
import com.taei.coupangclone.product.repository.ProductRepository;
import com.taei.coupangclone.user.entity.User;
import com.taei.coupangclone.user.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createCart(CreateCart createCart, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다.")
        );

        Product product = productRepository.findById(createCart.getProductId()).orElseThrow(
            () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );

        Cart cart = Cart.builder()
            .product(product)
            .user(user)
            .quantity(createCart.getQuantity())
            .build();

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCart(Long cartId, Long userId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
            () -> new IllegalArgumentException("삭제할 장바구니가 없습니다.")
        );

        if (!Objects.equals(cart.getUser().getId(), userId)) {
            throw new IllegalArgumentException("해당 장바구니에 대해 권한이 없습니다.");
        }

        cartRepository.delete(cart);
    }

    @Override
    public PageWrapper<ResponseCart> selectCarts(Pageable pageable, Long userId) {
        Page<ResponseCart> carts = cartRepository.selectProductByUserId(pageable, userId);

        return PageWrapper.of(carts);
    }
}
