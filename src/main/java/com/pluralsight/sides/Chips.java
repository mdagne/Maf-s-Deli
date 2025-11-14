package com.pluralsight.sides;

import com.pluralsight.services.PricingService;
import com.pluralsight.interfaces.Pricable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Chips implements Pricable interface
public class Chips implements Pricable {
    private static final List<String> AVAILABLE_CHIPS = Collections.unmodifiableList(Arrays.asList(
        "Salt & Vinegar", "BBQ", "Sour Cream & Onion", "Plain", "Jalapeño"
    ));

    private final String type;

    public Chips(String type) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public BigDecimal getPrice() {
        return PricingService.CHIPS_PRICE;
    }

    @Override
    public String toReceiptText() {
        return String.format("Chips: %s => $%s", type, getPrice().setScale(2).toPlainString());
    }

    public String getType() {
        return type;
    }

    // Returns list of all available chip flavors
    public static List<String> getAvailableChips() {
        return AVAILABLE_CHIPS;
    }
}

