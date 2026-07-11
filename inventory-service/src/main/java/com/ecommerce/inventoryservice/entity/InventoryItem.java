package com.ecommerce.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private Integer quantityAvailable;

    // 1. Getter bridge to satisfy original service code
    public String getProductName() {
        return this.sku;
    }

    // 2. Setter bridge to satisfy original service code
    public void setProductName(String productName) {
        this.sku = productName;
    }
}