package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        // Vegetables are included in the price
        return BigDecimal.ZERO;
    }

    public static List<String> getAvailableVegetables() {
        return AVAILABLE_VEGETABLES;
    }
}

