package com.taei.coupangclone.payment.repository;

import com.taei.coupangclone.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
