package com.ecommerce.inventoryservice.exception;

/**
 * Exception thrown when an operation would result in a duplicate SKU value.
 */
public class DuplicateSkuException extends RuntimeException {
    
    /**
     * Constructs a new DuplicateSkuException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DuplicateSkuException(String message) {
        super(message);
    }
}
