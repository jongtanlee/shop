package com.smoothlife.shop.seller.infrastructure;

import com.smoothlife.shop.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellerJpaRepository extends JpaRepository<Seller, UUID> {
}
