package com.ecommerce.inventoryservice.listener;

import com.ecommerce.inventoryservice.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @KafkaListener(topics = "order-created", groupId = "inventory-service-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Inventory service received order event: " + event.getOrderId());
        System.out.println("Need to reserve stock for: " + event.getProductName() +
                " quantity: " + event.getQuantity());
    }
}