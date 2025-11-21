package com.smoothlife.shop.settlement.infrastructure;

import com.smoothlife.shop.settlement.domain.SellerSettlement;
import com.smoothlife.shop.settlement.domain.SellerSettlementRepository;
import com.smoothlife.shop.settlement.domain.SettlementStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SellerSettlementRepositoryAdapter implements SellerSettlementRepository {

    private final SellerSettlementJpaRepository repository;

    public SellerSettlementRepositoryAdapter(SellerSettlementJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public SellerSettlement save(SellerSettlement settlement) {
        return repository.save(settlement);
    }

    @Override
    public List<SellerSettlement> findByStatus(SettlementStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<SellerSettlement> findByStatusAndSeller(SettlementStatus status, UUID sellerId) {
        return repository.findByStatusAndSellerId(status, sellerId);
    }

    @Override
    public void saveAll(List<SellerSettlement> settlements) {
        repository.saveAll(settlements);
    }
}
