package com.smoothlife.shop.product.domain;

import com.smoothlife.shop.product.application.dto.ProductCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(UUID id);

    Product save(Product product);

    void deleteById(UUID id);
}
