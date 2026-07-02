package com.ecommerce.orderservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(@Qualifier("loadBalancedBuilder") RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
    public String checkStock(String productName) {
        return restClient.get()
                .uri("http://inventory-service/api/inventory")
                .retrieve()
                .body(String.class);
    }

    public String inventoryFallback(String productName, Throwable t) {
        return "Inventory service unavailable — order queued for retry. Reason: " + t.getMessage();
    }
}