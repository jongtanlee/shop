package com.smoothlife.shop.payment.presentation;

import com.smoothlife.shop.common.ResponseEntity;
import com.smoothlife.shop.payment.application.PaymentService;
import com.smoothlife.shop.payment.application.dto.PaymentInfo;
import com.smoothlife.shop.payment.presentation.dto.PaymentRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1}/payments")
@AllArgsConstructor
//@RequiredArgsConstructor 둘중 하나만
public class PaymentController {
    private final PaymentService paymentService;

    /*@Autowired @RequiredArgsConstructor 또는 AllArgsConstructor 사용할 경우 필요 없음
    private PaymentService paymentInfoService;*/

    @Operation(summary = "결제 내역 조회", description = "확정된 결제 정보를 페이지 단위로 조회한다.")
    @GetMapping
    public ResponseEntity<List<PaymentInfo>> findAll(Pageable pageable) {
        return paymentService.findAll(pageable);
    }

    @Operation(summary = "토스 결제 승인", description = "토스 결제 완료 후 paymentKey/orderId/amount를 전달받아 결제를 승인한다.")
    @PostMapping("/confirm")
    public ResponseEntity<PaymentInfo> confirm(@RequestBody PaymentRequest request) {
        return paymentService.confirm(request.toCommand());
    }
}
