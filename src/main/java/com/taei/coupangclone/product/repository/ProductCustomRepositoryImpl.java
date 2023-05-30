package com.taei.coupangclone.product.repository;

import static com.taei.coupangclone.product.entity.QProduct.product;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.taei.coupangclone.product.dto.ResponseProduct;
import com.taei.coupangclone.product.entity.Product;
import com.taei.coupangclone.product.entity.SearchProduct;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements
    ProductCustomRepository {

    private JPAQueryFactory jpaQueryFactory;

    public ProductCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Product.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private BooleanExpression searchByProductName(String name) {

        return Objects.nonNull(name) ? product.name.contains(name) : null;
    }

    private BooleanExpression searchByProductSellerName(String sellerName) {

        return Objects.nonNull(sellerName) ? product.seller.sellerName.contains(sellerName) : null;
    }

    private BooleanExpression searchByProductPrice(Long minPrice, Long maxPrice) {
        if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice)) {
            return product.price.between(minPrice, maxPrice);
        } else if (Objects.nonNull(minPrice)) {
            return product.price.goe(minPrice); // 이상
        } else if (Objects.nonNull(maxPrice)) {
            return product.price.loe(maxPrice); // 이하
        } else {
            return null;
        }
    }

    private <T> JPAQuery<T> productsQuery(Expression<T> expr, SearchProduct searchProduct) {

        return jpaQueryFactory.select(expr)
            .from(product)
            .where(
                searchByProductName(searchProduct.getName()),
                searchByProductSellerName(searchProduct.getSellerName()),
                searchByProductPrice(searchProduct.getMinPrice(), searchProduct.getMaxPrice())
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseProduct> selectProductBySearchConditions(SearchProduct searchProduct,
        Pageable pageable) {
        List<ResponseProduct> products = productsQuery(
            Projections.constructor(
                ResponseProduct.class,
                product.name,
                product.detail,
                product.thumbnail,
                product.price,
                product.stock,
                product.seller.sellerName
            ), searchProduct
        )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long productCount = productsQuery(Wildcard.count, searchProduct).fetch().get(0);

        return new PageImpl<>(products, pageable, productCount);
    }
}
