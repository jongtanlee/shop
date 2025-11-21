package com.smoothlife.shop.payment.application;

import com.smoothlife.shop.common.ResponseEntity;
import com.smoothlife.shop.payment.application.dto.PaymentCommand;
import com.smoothlife.shop.payment.application.dto.PaymentFailCommand;
import com.smoothlife.shop.payment.application.dto.PaymentFailureInfo;
import com.smoothlife.shop.payment.application.dto.PaymentInfo;
import com.smoothlife.shop.payment.client.TossPaymentClient;
import com.smoothlife.shop.payment.client.dto.TossPaymentResponse;
import com.smoothlife.shop.payment.domain.Payment;
import com.smoothlife.shop.payment.domain.PaymentFailure;
import com.smoothlife.shop.payment.domain.PaymentFailureRepository;
import com.smoothlife.shop.payment.domain.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//@AllArgsConstructor
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TossPaymentClient tossPaymentClient;
    private final PaymentFailureRepository paymentFailureRepository;
//    private final SellerSettlementRepository sellerSettlementRepository;
//    private final TossPaymentClient tossPaymentClient;
//    private final OrderService orderService;

    //public PaymentService(PaymentRepository paymentRepository//,
                          /*PaymentFailureRepository paymentFailureRepository,
                          SellerSettlementRepository sellerSettlementRepository,
                          TossPaymentClient tossPaymentClient,
                          OrderService orderService) {*/
       // this.paymentRepository = paymentRepository;
        /*this.paymentFailureRepository = paymentFailureRepository;
        this.sellerSettlementRepository = sellerSettlementRepository;
        this.tossPaymentClient = tossPaymentClient;
        this.orderService = orderService;*/
   // }

    // 결제
    public ResponseEntity<List<PaymentInfo>> findAll(Pageable pageable) {
        Page<Payment> page = paymentRepository.findAll(pageable);
        List<PaymentInfo> paymentInfos = page.stream()
                .map(PaymentInfo::from) //item->paymentInfo.from(item) 과 동일
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), paymentInfos, page.getTotalElements());
    }

    public ResponseEntity<PaymentInfo> confirm(PaymentCommand command) {
        TossPaymentResponse tossPayment = tossPaymentClient.confirm(command);
        //UUID orderId = UUID.fromString(tossPayment.orderId());
        //PurchaseOrder order = orderService.findEntity(orderId);
        Payment payment = Payment.create(
                tossPayment.paymentKey(),
                tossPayment.orderId(),
                tossPayment.totalAmount()
        );
        LocalDateTime approvedAt = tossPayment.approvedAt() != null ? tossPayment.approvedAt().toLocalDateTime() : null;
        LocalDateTime requestedAt = tossPayment.requestedAt() != null ? tossPayment.requestedAt().toLocalDateTime() : null;

        payment.markConfirmed(tossPayment.method(), approvedAt, requestedAt);

        Payment saved = paymentRepository.save(payment);
//        orderService.markPaid(order);
//        SellerSettlement settlement = SellerSettlement.create(
//                order.getSellerId(),
//                order.getId(),
//                order.getAmount()
//        );
//        sellerSettlementRepository.save(settlement);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), PaymentInfo.from(saved), 1);
    }

    public ResponseEntity<PaymentFailureInfo> recordFailure(PaymentFailCommand command) {
        PaymentFailure failure = PaymentFailure.from(
                command.orderId(),
                command.paymentKey(),
                command.errorCode(),
                command.errorMessage(),
                command.amount(),
                command.rawPayload()
        );
        PaymentFailure saved = paymentFailureRepository.save(failure);
        return new ResponseEntity<>(HttpStatus.OK.value(), PaymentFailureInfo.from(saved), 1);
    }
}