package com.taei.coupangclone.order.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.taei.coupangclone.order.entity.QOrder.order;
import static com.taei.coupangclone.orderitem.entity.QOrderItem.orderItem;
import static com.taei.coupangclone.product.entity.QProduct.product;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.taei.coupangclone.order.dto.ResponseUserOrders;
import com.taei.coupangclone.order.entity.Order;
import com.taei.coupangclone.orderitem.dto.ResponseUserOrderItem;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderCustomRepositoryImpl extends QuerydslRepositorySupport implements
    OrderCustomRepository {

    private JPAQueryFactory jpaQueryFactory;

    public OrderCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Order.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private BooleanExpression searchByUserId(Long userId) {

        return Objects.nonNull(userId) ? order.user.id.eq(userId) : null;
    }

    private <T> JPAQuery<T> userOrderQuery(Expression<T> expr, Long userId) {

        return jpaQueryFactory.select(expr)
            .from(order)
            .join(orderItem).on(orderItem.order.id.eq(order.id))
            .join(product).on(orderItem.product.id.eq(product.id))
            .where(
                searchByUserId(userId)
            );
    }

    @Override
    @Transactional
    public Page<ResponseUserOrders> selectUserOrders(Long userId, Pageable pageable) {
        List<ResponseUserOrders> userOrders = jpaQueryFactory.from(order)
            .innerJoin(orderItem)
            .on(order.id.eq(orderItem.order.id))
            .where(searchByUserId(userId))
            .transform(
                groupBy(order.id).list(
                    Projections.fields(
                        ResponseUserOrders.class,
                        order.id,
                        order.totalPrice,
                        list(
                            Projections.fields(
                                ResponseUserOrderItem.class,
                                orderItem.id,
                                orderItem.count,
                                orderItem.orderPrice,
                                orderItem.product.id.as("productId"),
                                orderItem.product.name.as("productName")
                            )
                        ).as("userOrderItems")
                    )
                )
            );

        Long userOrderCount = jpaQueryFactory.select(Wildcard.count).from(order).where(
            searchByUserId(userId)
        ).fetch().get(0);

        return new PageImpl<>(userOrders, pageable, userOrderCount);
    }


}
