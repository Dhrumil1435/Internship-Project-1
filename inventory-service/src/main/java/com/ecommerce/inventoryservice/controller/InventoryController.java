package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public Inventory addItem(@RequestBody Inventory item) {
        return inventoryService.addItem(item);
    }

    @GetMapping
    public List<Inventory> getAllItems() {
        return inventoryService.getAllItems();
    }

    @GetMapping("/{id}")
    public Inventory getItemById(@PathVariable Long id) {
        return inventoryService.getItemById(id);
    }

    @PutMapping("/{id}")
    public Inventory updateItem(@PathVariable Long id, @RequestBody Inventory item) {
        return inventoryService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        inventoryService.deleteItem(id);
    }
}