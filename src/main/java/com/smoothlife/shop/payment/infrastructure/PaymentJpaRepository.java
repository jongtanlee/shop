package com.smoothlife.shop.payment.infrastructure;

import com.smoothlife.shop.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {
}
