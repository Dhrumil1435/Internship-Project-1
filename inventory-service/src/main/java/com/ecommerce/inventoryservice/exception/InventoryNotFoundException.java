package com.ecommerce.inventoryservice.exception;

/**
 * Exception thrown when a requested inventory item cannot be found.
 */
public class InventoryNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new InventoryNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
