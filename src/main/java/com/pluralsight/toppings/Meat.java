package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.services.PricingService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Meat extends abstract Topping class
public class Meat extends Topping {
    private static final List<String> AVAILABLE_MEATS = Collections.unmodifiableList(Arrays.asList(
        "Steak", "Ham", "Salami", "Roast Beef", "Chicken", "Bacon"
    ));

    public Meat(String name) {
        super(name, true);
    }

    @Override
    public BigDecimal getPriceBySize(SandwichSize size) {
        return PricingService.getMeatPrice(size);
    }
    
    @Override
    public BigDecimal getExtraPrice(SandwichSize size) {
        return PricingService.getExtraMeatPrice(size);
    }

    // Returns list of all available meat options
    public static List<String> getAvailableMeats() {
        return AVAILABLE_MEATS;
    }
}

