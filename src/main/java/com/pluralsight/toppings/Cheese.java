package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.services.PricingService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Cheese extends abstract Topping class
public class Cheese extends Topping {
    private static final List<String> AVAILABLE_CHEESES = Collections.unmodifiableList(Arrays.asList(
        "American", "Provolone", "Cheddar", "Swiss"
    ));

    public Cheese(String name) {
        super(name, true);
    }

    @Override
    public BigDecimal getPriceBySize(SandwichSize size) {
        return PricingService.getCheesePrice(size);
    }
    
    @Override
    public BigDecimal getExtraPrice(SandwichSize size) {
        return PricingService.getExtraCheesePrice(size);
    }

    // Returns list of all available cheese options
    public static List<String> getAvailableCheeses() {
        return AVAILABLE_CHEESES;
    }
}

