package com.taei.coupangclone.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {

    @PersistenceContext // 영속성 컨텍스트를 주입하는 어노테이션
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {

        return new JPAQueryFactory(entityManager);
    }
}
