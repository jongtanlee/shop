package com.smoothlife.shop.search;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "인덱스 설정",
        name = "{\n  \"numberOfShards\": 3,\n  \"numberOfReplicas\": 0\n}"
)
// 인덱스 생성 시 샤드/레플리카 설정 요청 DTO
public record IndexConfigRequest(Integer numberOfShards, Integer numberOfReplicas) {
}
