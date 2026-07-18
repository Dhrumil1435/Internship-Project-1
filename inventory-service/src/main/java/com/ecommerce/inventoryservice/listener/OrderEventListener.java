package com.ecommerce.inventoryservice.listener;

import com.ecommerce.inventoryservice.entity.InventoryItem;
import com.ecommerce.inventoryservice.event.OrderCreatedEvent;
import com.ecommerce.inventoryservice.event.InventoryReservedEvent;
import com.ecommerce.inventoryservice.event.InventoryFailedEvent;
import com.ecommerce.inventoryservice.event.PaymentFailedEvent;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
public class OrderEventListener {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventListener(InventoryRepository inventoryRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-created-topic", groupId = "inventory-group")
    @Transactional
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Processing inventory allocation for Order ID: {}", event.getOrderId());

        String prodSku = event.getProductName() != null ? event.getProductName() : "Laptop";
        Integer qtyRequested = event.getQuantity() != null ? event.getQuantity() : 1;
        BigDecimal price = event.getTotalAmount() != null ? event.getTotalAmount() : BigDecimal.valueOf(999.99);

        Optional<InventoryItem> inventoryOpt = inventoryRepository.findBySku(prodSku);

        if (inventoryOpt.isPresent() && inventoryOpt.get().getQuantityAvailable() >= qtyRequested) {
            InventoryItem item = inventoryOpt.get();
            item.setQuantityAvailable(item.getQuantityAvailable() - qtyRequested);
            inventoryRepository.save(item);

            log.info("Stock reserved successfully for SKU: {}", prodSku);

            InventoryReservedEvent reservedEvent = new InventoryReservedEvent(
                    event.getOrderId(),
                    prodSku,
                    qtyRequested,
                    price
            );
            kafkaTemplate.send("inventory-reserved-topic", reservedEvent);
        } else {
            log.warn("Inventory validation failed for SKU: {}. Insufficient stock.", prodSku);

            InventoryFailedEvent failedEvent = new InventoryFailedEvent(
                    event.getOrderId(),
                    "INSUFFICIENT_STOCK_OR_PRODUCT_NOT_FOUND"
            );
            kafkaTemplate.send("inventory-failed-topic", failedEvent);
        }
    }

    @KafkaListener(topics = "payment-failed-topic", groupId = "inventory-payment-group")
    @Transactional
    public void onPaymentFailed(PaymentFailedEvent event) {
        log.warn("Received PaymentFailedEvent for Order ID: {} | Reason: {}. Reverting stock.", event.getOrderId(), event.getReason());
        if (event.getProductName() != null && event.getQuantity() != null) {
            Optional<InventoryItem> inventoryOpt = inventoryRepository.findBySku(event.getProductName());
            if (inventoryOpt.isPresent()) {
                InventoryItem item = inventoryOpt.get();
                item.setQuantityAvailable(item.getQuantityAvailable() + event.getQuantity());
                inventoryRepository.save(item);
                log.info("Successfully rolled back stock. Restored {} units of SKU: {}", event.getQuantity(), event.getProductName());
            } else {
                log.error("Could not rollback stock. SKU not found: {}", event.getProductName());
            }
        } else {
            log.error("Could not rollback stock. Event is missing product name or quantity details.");
        }
    }
}