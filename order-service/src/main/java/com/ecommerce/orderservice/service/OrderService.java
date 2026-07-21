package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.event.OrderCreatedEvent;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderService(KafkaTemplate<String, Object> kafkaTemplate, OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    // 1. Create Order and Send Phase 5 Kafka Event
    public Order createOrder(Order order) {
        log.info("Creating order for customer: {}, product: {}", order.getCustomerName(), order.getProductName());
        order.setCreatedAt(LocalDateTime.now());

        // Check if inventory service is available using the circuit breaker client
        String checkResult = inventoryClient.checkStock(order.getProductName());
        if (checkResult != null && checkResult.startsWith("Inventory service unavailable")) {
            order.setStatus("QUEUED");
            return orderRepository.save(order);
        }

        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(savedOrder.getId());
        event.setProductName(savedOrder.getProductName());
        event.setQuantity(savedOrder.getQuantity());
        event.setTotalAmount(savedOrder.getTotalAmount());

        kafkaTemplate.send("order-created-topic", event);

        return savedOrder;
    }

    // 2. Get All Orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 3. Get Order By ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // 4. Update Order
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setProductName(orderDetails.getProductName());
        order.setQuantity(orderDetails.getQuantity());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setStatus(orderDetails.getStatus());

        return orderRepository.save(order);
    }

    // 5. Delete Order
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}