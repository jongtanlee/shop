package com.smoothlife.shop.payment.presentation.dto;

import com.smoothlife.shop.payment.application.dto.PaymentFailCommand;

/**
 * 결제 실패 콜백을 받기 위한 요청 DTO.
 */
public record PaymentFailRequest(
        String orderId,
        String paymentKey,
        String code,
        String message,
        Long amount,
        String rawPayload
) {

    public PaymentFailCommand toCommand() {
        return new PaymentFailCommand(orderId, paymentKey, code, message, amount, rawPayload);
    }
}
