package com.smoothlife.shop.payment.infrastructure;

import com.smoothlife.shop.payment.domain.Payment;
import com.smoothlife.shop.payment.domain.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
//@AllArgsConstructor
// AllArgsConstructor 를 사용한다면 @AutoWired 사용할 필요 없음
public class PaymentRepositoryAdapter implements PaymentRepository {
    /*@Autowired
    private PaymentJpaRepository paymentJpaRepository;*/

    private final PaymentJpaRepository paymentJpaRepository;

    public PaymentRepositoryAdapter(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return paymentJpaRepository.findById(id);
    }

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }
}
