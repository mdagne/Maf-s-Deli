package com.pluralsight.services;

import com.pluralsight.enums.SandwichSize;

import java.math.BigDecimal;
import java.util.Objects;

// Centralized prices as constants; consider loading from properties or JSON for improvements.
public final class PricingService {
    // Bread base prices
    public static final BigDecimal BREAD_4 = new BigDecimal("5.50");
    public static final BigDecimal BREAD_8 = new BigDecimal("7.00");
    public static final BigDecimal BREAD_12 = new BigDecimal("8.50");

    // Meat prices per size
    public static final BigDecimal MEAT_4 = new BigDecimal("1.00");
    public static final BigDecimal MEAT_8 = new BigDecimal("2.00");
    public static final BigDecimal MEAT_12 = new BigDecimal("3.00");

    // Extra meat
    public static final BigDecimal EXTRA_MEAT_4 = new BigDecimal("0.50");
    public static final BigDecimal EXTRA_MEAT_8 = new BigDecimal("1.00");
    public static final BigDecimal EXTRA_MEAT_12 = new BigDecimal("1.50");

    // Cheese prices per size
    public static final BigDecimal CHEESE_4 = new BigDecimal("0.75");
    public static final BigDecimal CHEESE_8 = new BigDecimal("1.50");
    public static final BigDecimal CHEESE_12 = new BigDecimal("2.25");

    // Extra cheese
    public static final BigDecimal EXTRA_CHEESE_4 = new BigDecimal("0.30");
    public static final BigDecimal EXTRA_CHEESE_8 = new BigDecimal("0.60");
    public static final BigDecimal EXTRA_CHEESE_12 = new BigDecimal("0.90");

    // Drinks and chips
    public static final BigDecimal DRINK_SMALL = new BigDecimal("2.00");
    public static final BigDecimal DRINK_MEDIUM = new BigDecimal("2.50");
    public static final BigDecimal DRINK_LARGE = new BigDecimal("3.00");
    public static final BigDecimal CHIPS_PRICE = new BigDecimal("1.50");

    private PricingService() {
        // Prevent instantiation
    }

    public static BigDecimal getBreadPrice(SandwichSize size) {
        switch (Objects.requireNonNull(size)) {
            case FOUR: return BREAD_4;
            case EIGHT: return BREAD_8;
            case TWELVE: return BREAD_12;
            default: return BREAD_8;
        }
    }

    public static BigDecimal getMeatPrice(SandwichSize size) {
        switch (Objects.requireNonNull(size)) {
            case FOUR: return MEAT_4;
            case EIGHT: return MEAT_8;
            case TWELVE: return MEAT_12;
            default: return MEAT_8;
        }
    }

    public static BigDecimal getExtraMeatPrice(SandwichSize size) {
        switch (Objects.requireNonNull(size)) {
            case FOUR: return EXTRA_MEAT_4;
            case EIGHT: return EXTRA_MEAT_8;
            case TWELVE: return EXTRA_MEAT_12;
            default: return EXTRA_MEAT_8;
        }
    }

    public static BigDecimal getCheesePrice(SandwichSize size) {
        switch (Objects.requireNonNull(size)) {
            case FOUR: return CHEESE_4;
            case EIGHT: return CHEESE_8;
            case TWELVE: return CHEESE_12;
            default: return CHEESE_8;
        }
    }

    public static BigDecimal getExtraCheesePrice(SandwichSize size) {
        switch (Objects.requireNonNull(size)) {
            case FOUR: return EXTRA_CHEESE_4;
            case EIGHT: return EXTRA_CHEESE_8;
            case TWELVE: return EXTRA_CHEESE_12;
            default: return EXTRA_CHEESE_8;
        }
    }
}
