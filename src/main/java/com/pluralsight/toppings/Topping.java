package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;

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

    // Abstract method - subclasses supply size-based pricing
    public abstract java.math.BigDecimal getPriceBySize(SandwichSize size);
}

