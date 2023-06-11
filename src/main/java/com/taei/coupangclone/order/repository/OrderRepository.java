package com.taei.coupangclone.order.repository;

import com.taei.coupangclone.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {

}
