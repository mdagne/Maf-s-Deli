package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Sauce extends abstract Topping class
public class Sauce extends Topping {
    private static final List<String> AVAILABLE_SAUCES = Collections.unmodifiableList(Arrays.asList(
        "Mayo", "Mustard", "Ketchup", "Ranch", "Chipotle", "Vinaigrette"
    ));

    public Sauce(String name) {
        super(name, false);
    }

    @Override
    public BigDecimal getPriceBySize(SandwichSize size) {
        return BigDecimal.ZERO;
    }

    // Returns list of all available sauce options
    public static List<String> getAvailableSauces() {
        return AVAILABLE_SAUCES;
    }
}

