package com.ecommerce.orderservice.listener;

import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.event.PaymentCompletedEvent;
import com.ecommerce.orderservice.event.PaymentFailedEvent;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class PaymentEventListener {

    private final OrderRepository orderRepository;

    public PaymentEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "payment-completed-topic", groupId = "order-payment-group")
    @Transactional
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received PaymentCompletedEvent for Order ID: {}", event.getOrderId());

        Optional<Order> orderOpt = orderRepository.findById(event.getOrderId());
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus("CONFIRMED"); // The Saga has successfully finished!
            orderRepository.save(order);
            log.info("Order ID: {} has been successfully CONFIRMED.", event.getOrderId());
        }
    }

    @KafkaListener(topics = "payment-failed-topic", groupId = "order-payment-group")
    @Transactional
    public void onPaymentFailed(PaymentFailedEvent event) {
        log.warn("Received PaymentFailedEvent for Order ID: {} | Reason: {}", event.getOrderId(), event.getReason());

        Optional<Order> orderOpt = orderRepository.findById(event.getOrderId());
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus("FAILED"); // Mark order as failed due to billing issues
            orderRepository.save(order);
            // In a full saga, this would also trigger a roll-back event to restore inventory stock!
        }
    }
}