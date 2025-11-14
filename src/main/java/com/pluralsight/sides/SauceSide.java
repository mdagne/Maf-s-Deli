package com.pluralsight.sides;

import com.pluralsight.interfaces.Pricable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Represents free side sauces that can be added to orders.
public class SauceSide implements Pricable {
    private static final List<String> AVAILABLE_SAUCE_SIDES = Collections.unmodifiableList(Arrays.asList(
        "Au Jus",
        "Coleslaw"
    ));

    private final String name;

    public SauceSide(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public BigDecimal getPrice() {
        // Sauce sides are included (free)
        return BigDecimal.ZERO;
    }

    @Override
    public String toReceiptText() {
        return String.format("Sauce: %s => $%s", name, getPrice().setScale(2).toPlainString());
    }

    public String getName() {
        return name;
    }

    public static List<String> getAvailableSauceSides() {
        return AVAILABLE_SAUCE_SIDES;
    }
}

