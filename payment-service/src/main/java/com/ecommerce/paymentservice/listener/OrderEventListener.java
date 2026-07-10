package com.ecommerce.paymentservice.listener;

import com.ecommerce.paymentservice.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    @KafkaListener(topics = "order-created-topic", groupId = "payment-group")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("#### -> Payment Service successfully heard event -> Order ID: {}", event.getOrderId());
        // No business logic yet for Phase 4
    }
}