package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    // Spring Boot will automatically write a 'SELECT * WHERE sku = ?' query behind the scenes
    Optional<InventoryItem> findBySku(String sku);
}