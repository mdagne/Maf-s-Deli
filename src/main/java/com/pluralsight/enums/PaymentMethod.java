package com.pluralsight.enums;

// Enum representing payment methods
public enum PaymentMethod {
    CASH("Cash"),
    CARD("Card");
    
    private final String displayName;
    
    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
