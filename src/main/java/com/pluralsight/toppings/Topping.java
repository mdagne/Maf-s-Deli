package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;

// Topping is the base class for all sandwich toppings, providing price calculation by size and extra pricing.
public abstract class Topping {
    protected final String name;
    protected final boolean premium; // true for meat/cheese

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

    public abstract java.math.BigDecimal getPriceBySize(SandwichSize size);
    
    public java.math.BigDecimal getExtraPrice(SandwichSize size) {
        return java.math.BigDecimal.ZERO;
    }
}

