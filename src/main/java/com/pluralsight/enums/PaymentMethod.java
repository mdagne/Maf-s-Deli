package com.pluralsight.enums;

//  payment options (Cash, Card) with display names
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
