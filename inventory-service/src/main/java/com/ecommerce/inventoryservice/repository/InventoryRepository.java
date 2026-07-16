package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Inventory entities.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Finds an inventory item by its associated product ID.
     *
     * @param productId the product ID.
     * @return an Optional containing the found inventory item, or empty if not found.
     */
    Optional<Inventory> findByProductId(Long productId);

    /**
     * Finds an inventory item by its unique SKU code.
     *
     * @param sku the SKU code.
     * @return an Optional containing the found inventory item, or empty if not found.
     */
    Optional<Inventory> findBySku(String sku);

    /**
     * Checks if an inventory item exists with the specified SKU code.
     *
     * @param sku the SKU code.
     * @return true if an item exists, false otherwise.
     */
    boolean existsBySku(String sku);
}