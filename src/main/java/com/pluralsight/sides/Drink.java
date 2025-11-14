package com.pluralsight.sides;

import com.pluralsight.enums.DrinkSize;
import com.pluralsight.services.PricingService;
import com.pluralsight.interfaces.Pricable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Drink implements Pricable interface
public class Drink implements Pricable {
    private static final List<String> AVAILABLE_DRINKS = Collections.unmodifiableList(Arrays.asList(
        "Coca Cola", "Pepsi", "Sprite", "Dr. Pepper", "Fanta", 
        "Orange Juice", "Apple Juice", "Iced Tea", "Lemonade", "Water"
    ));

    private final String flavor;
    private final DrinkSize size;

    public Drink(String flavor, DrinkSize size) {
        this.flavor = Objects.requireNonNull(flavor);
        this.size = Objects.requireNonNull(size);
    }

    @Override
    public BigDecimal getPrice() {
        switch (size) {
            case SMALL: return PricingService.DRINK_SMALL;
            case MEDIUM: return PricingService.DRINK_MEDIUM;
            case LARGE: return PricingService.DRINK_LARGE;
            default: return PricingService.DRINK_MEDIUM;
        }
    }

    @Override
    public String toReceiptText() {
        return String.format("Drink: %s (%s) => $%s", flavor, size.name(), getPrice().setScale(2).toPlainString());
    }

    public DrinkSize getSize() {
        return size;
    }

    // Returns list of all available drink flavors
    public static List<String> getAvailableDrinks() {
        return AVAILABLE_DRINKS;
    }
}

