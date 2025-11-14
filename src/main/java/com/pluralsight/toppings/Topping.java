package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;

// Abstract base class for all toppings
public abstract class Topping {
    protected final String name;
    protected final boolean premium;

    public Topping(String name, boolean premium) {
        this.name = name;
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public boolean isPremium() {
        return premium;
    }

    // Abstract method that must be implemented by subclasses
    public abstract java.math.BigDecimal getPriceBySize(SandwichSize size);
    
    // Can be overridden by subclasses
    public java.math.BigDecimal getExtraPrice(SandwichSize size) {
        return java.math.BigDecimal.ZERO;
    }
}

