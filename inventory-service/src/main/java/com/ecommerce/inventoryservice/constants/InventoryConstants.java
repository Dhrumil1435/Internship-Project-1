package com.ecommerce.inventoryservice.constants;

/**
 * Constants related to the Inventory domain.
 */
public final class InventoryConstants {

    private InventoryConstants() {
        // Prevent instantiation
    }

    public static final String TABLE_NAME = "inventory";
    public static final String SKU_PATTERN = "^[a-zA-Z0-9-_]+$";
    
    // Exception messages
    public static final String ERROR_NOT_FOUND = "Inventory item not found with id: %d";
    public static final String ERROR_PRODUCT_NOT_FOUND = "Inventory item not found for product ID: %d";
    public static final String ERROR_SKU_NOT_FOUND = "Inventory item not found for SKU: %s";
    public static final String ERROR_DUPLICATE_SKU = "Inventory item already exists for SKU: %s";
}
