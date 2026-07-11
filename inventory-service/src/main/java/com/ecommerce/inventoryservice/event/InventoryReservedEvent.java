package com.ecommerce.inventoryservice.event; // Adjust package name per service

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReservedEvent {
    private Long orderId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalAmount;
}