package com.ecommerce.paymentservice.listener;

import com.ecommerce.paymentservice.event.InventoryReservedEvent;
import com.ecommerce.paymentservice.event.PaymentCompletedEvent;
import com.ecommerce.paymentservice.event.PaymentFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventListener(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "inventory-reserved-topic", groupId = "payment-group")
    public void onInventoryReserved(InventoryReservedEvent event) {
        log.info("Processing payment for Order ID: {} | Total Amount: ${}", event.getOrderId(), event.getTotalAmount());

        // Simulate a simple fraud/balance check rule
        // If order amount is suspiciously high (e.g., > $5000), fail the payment to test rollbacks later
        if (event.getTotalAmount().doubleValue() > 5000.00) {
            log.warn("Payment declined for Order ID: {} due to credit limit violation.", event.getOrderId());

            PaymentFailedEvent failedEvent = new PaymentFailedEvent(event.getOrderId(), "CREDIT_LIMIT_EXCEEDED");
            kafkaTemplate.send("payment-failed-topic", failedEvent);
        } else {
            log.info("Payment captured successfully for Order ID: {}", event.getOrderId());

            // Generate a dummy transaction ID for tracking
            Long mockPaymentId = System.currentTimeMillis();
            PaymentCompletedEvent completedEvent = new PaymentCompletedEvent(
                    event.getOrderId(),
                    mockPaymentId,
                    "SUCCESS"
            );
            kafkaTemplate.send("payment-completed-topic", completedEvent);
        }
    }
}