package com.ecommerce.Demo.service;

import com.ecommerce.Demo.entity.Order;
import com.ecommerce.Demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existing = getOrderById(id);
        existing.setStatus(updatedOrder.getStatus());
        existing.setQuantity(updatedOrder.getQuantity());
        existing.setTotalAmount(updatedOrder.getTotalAmount());
        return orderRepository.save(existing);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}