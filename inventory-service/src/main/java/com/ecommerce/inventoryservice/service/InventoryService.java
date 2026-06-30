package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.entity.InventoryItem;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryItem addItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }

    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    public InventoryItem getItemById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    public InventoryItem updateItem(Long id, InventoryItem updated) {
        InventoryItem existing = getItemById(id);
        existing.setProductName(updated.getProductName());
        existing.setQuantityAvailable(updated.getQuantityAvailable());
        existing.setSku(updated.getSku());
        return inventoryRepository.save(existing);
    }

    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }
}