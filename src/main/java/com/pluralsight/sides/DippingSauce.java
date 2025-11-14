package com.pluralsight.sides;

import com.pluralsight.interfaces.Pricable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Represents free dipping sauces and side items that can be added to orders
public class DippingSauce implements Pricable {
    private static final List<String> AVAILABLE_DIPPING_SAUCES = Collections.unmodifiableList(Arrays.asList(
        "Au Jus",
        "Maf Sauce"
    ));

    private final String name;

    public DippingSauce(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public BigDecimal getPrice() {
        return BigDecimal.ZERO;
    }

    @Override
    public String toReceiptText() {
        return String.format("Side: %s => $%s", name, getPrice().setScale(2).toPlainString());
    }

    public String getName() {
        return name;
    }

    // Returns list of all available dipping sauce options
    public static List<String> getAvailableDippingSauces() {
        return AVAILABLE_DIPPING_SAUCES;
    }
}

