package com.smoothlife.shop.kafka.dto;

public record AsyncOrderDispatchResult(
        String orderId,
        String topic,
        int partition,
        long offset
) {
}
