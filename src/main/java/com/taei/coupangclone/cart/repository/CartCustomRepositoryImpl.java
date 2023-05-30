package com.taei.coupangclone.cart.repository;

import static com.taei.coupangclone.cart.entity.QCart.cart;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.taei.coupangclone.cart.dto.ResponseCart;
import com.taei.coupangclone.cart.entity.Cart;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class CartCustomRepositoryImpl extends QuerydslRepositorySupport implements
    CartCustomRepository {

    private JPAQueryFactory jpaQueryFactory;

    public CartCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Cart.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private BooleanExpression searchByUserId(Long userId) {
        return Objects.nonNull(userId) ? cart.user.id.eq(userId) : null;
    }

    @Override
    public Page<ResponseCart> selectProductByUserId(Pageable pageable, Long userId) {
        List<ResponseCart> carts = jpaQueryFactory.select(
                Projections.constructor(
                    ResponseCart.class,
                    cart.id,
                    cart.product.id,
                    cart.product.name,
                    cart.product.price,
                    cart.product.seller.sellerName
                )
            ).from(cart)
            .where(
                searchByUserId(userId)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long cartCount = jpaQueryFactory.select(Wildcard.count)
            .from(cart)
            .where(
                searchByUserId(userId)
            )
            .fetch().get(0);

        return new PageImpl<>(carts, pageable, cartCount);
    }
}
