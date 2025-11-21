package com.smoothlife.shop.settlement.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1}/settlements")
@RequiredArgsConstructor
public class SettlementJobController {
    private final JobLauncher jobLauncher;
    private final Job sellerSettlementJob;

    @Operation(summary = "전체 판매자 배치 실행", description = "모든 판매자의 배치를 실행한다.")
    @PostMapping("/run/all")
    public ResponseEntity<String> runAll() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("timestop", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(sellerSettlementJob, params);
        return ResponseEntity.ok("Settlement job (all sellers) started");
    }

    @Operation(summary = "특정 판매자 배치 실행", description = "판매자의 아이디로 배치를 실행한다.")
    @PostMapping("/run/seller")
    public ResponseEntity<String> runSeller(@RequestParam("sellerId") String sellerId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("sellerId", sellerId)
                .toJobParameters();
        jobLauncher.run(sellerSettlementJob, params);
        return ResponseEntity.ok("Settlement job started for seller :" + sellerId);
    }
}
