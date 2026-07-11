package com.ecommerce.inventoryservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // <-- CRITICAL: Jackson requires a no-args constructor to instantiate the object!
public class PaymentFailedEvent {
    private Long orderId;
    private String reason;
}