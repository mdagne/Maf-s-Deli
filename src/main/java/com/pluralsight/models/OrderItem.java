package com.pluralsight.models;

import java.math.BigDecimal;

// Abstract base class for all orderable items demonstrating inheritance and abstract methods.
public abstract class OrderItem implements MenuItem {
    protected final String name;
    protected final BigDecimal price;
    
    // Constructor for OrderItem with inheritance
    public OrderItem(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
    
    // Abstract method that subclasses must implement
    @Override
    public abstract String getDescription();
    
    // Template method with default implementation
    @Override
    public abstract String getCategory();
    
    @Override
    public BigDecimal getPrice() {
        return price;
    }
    
    @Override
    public String toReceiptText() {
        return String.format("%s => $%s", getDescription(), getPrice().setScale(2).toPlainString());
    }
    
    public String getName() {
        return name;
    }
}
