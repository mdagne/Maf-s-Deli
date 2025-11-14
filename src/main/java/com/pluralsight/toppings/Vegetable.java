package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Vegetable extends abstract Topping class
public class Vegetable extends Topping {
    private static final List<String> AVAILABLE_VEGETABLES = Collections.unmodifiableList(Arrays.asList(
        "Lettuce", "Peppers", "Onions", "Tomatoes", "Jalapeños", 
        "Cucumbers", "Pickles", "Guacamole", "Mushrooms"
    ));

    public Vegetable(String name) {
        super(name, false);
    }

    @Override
    public BigDecimal getPriceBySize(SandwichSize size) {
        return BigDecimal.ZERO;
    }

    // Returns list of all available vegetable options
    public static List<String> getAvailableVegetables() {
        return AVAILABLE_VEGETABLES;
    }
}

