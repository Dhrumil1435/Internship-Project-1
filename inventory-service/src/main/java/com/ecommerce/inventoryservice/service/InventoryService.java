package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.exception.InventoryNotFoundException;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory addItem(Inventory item) {
        return inventoryRepository.save(item);
    }

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Inventory getItemById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Item not found with id: " + id));
    }

    public Inventory updateItem(Long id, Inventory updated) {
        Inventory existing = getItemById(id);
        existing.setProductId(updated.getProductId());
        existing.setProductName(updated.getProductName());
        existing.setSku(updated.getSku());
        existing.setQuantity(updated.getQuantity());
        existing.setReservedQuantity(updated.getReservedQuantity());
        existing.setPrice(updated.getPrice());
        return inventoryRepository.save(existing);
    }

    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }
}