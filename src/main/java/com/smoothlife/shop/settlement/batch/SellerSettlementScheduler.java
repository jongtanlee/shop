package com.smoothlife.shop.settlement.batch;

import com.smoothlife.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SellerSettlementScheduler {
    private  static final Logger log = LoggerFactory.getLogger(SellerSettlementScheduler.class);
    private final SellerRepository sellerRepository;
    private final JobLauncher jobLauncher;
    private final Job sellerSettlementJob;
    private final ThreadPoolTaskExecutor settlementTaskExecutor;
    @Value("${settlement.async.enabled:false}")
    private boolean settlementAsyncEnabled;


    @Scheduled(cron = "${spring.task.scheduling.cron.settlement}")
    public void runMidnightSettlements() {
        Pageable pageable = Pageable.ofSize(100);
        Page<UUID> page;
        do {
            page = sellerRepository.findAll(pageable).map(seller -> seller.getId());
            List<UUID> sellerIds = page.getContent();
            if (sellerIds.isEmpty()) {
                break;
            }
            sellerIds.forEach(this::runJobForSeller);
            log.info("Settlement batch chunk for {} sellers (page {}/{})",
                    sellerIds.size(), page.getNumber() + 1, page.getTotalPages());
            pageable = page.hasNext() ? page.nextPageable() : Pageable.unpaged();
        } while (page.hasNext());
    }

    /*
    * sellerId에 대해 정산 Job을 실행한다.
    * 비동기 설정 시 별도의 스레드에서 실행하고, 아니면 동기 실행한다.
    */
    private void runJobForSeller(UUID sellerId) {
        /*new Thread() {
            @Override
            public void run() {
                super.run();
                // 할일
            }
        }.start(); 이런 방식을 아래 execute로 사용한다*/

        try{
            Runnable executeJob = () -> {   // 람다식
                try {
                    JobParameters params = new JobParametersBuilder()
                            .addLong("timestamp", System.currentTimeMillis())
                            .addString("sellerId", sellerId.toString())
                            .toJobParameters();
                    jobLauncher.run(sellerSettlementJob, params);
                    log.info("Settlement job triggered for seller {}", sellerId);
                }catch (Exception ex) {
                    log.error("Failed to run settlement job for seller {}", sellerId, ex);
                }
            }; //Runnable 정의

            if (settlementAsyncEnabled) {
                settlementTaskExecutor.execute(executeJob);
            } else {
                executeJob.run();
            }
        }catch (Exception ex) {
            log.error("Failed to run settlement job for seller {}", sellerId, ex);
        }
    }
}
