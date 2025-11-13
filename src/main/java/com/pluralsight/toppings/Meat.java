package com.pluralsight.toppings;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.services.PricingService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static List<String> getAvailableMeats() {
        return AVAILABLE_MEATS;
    }
}

