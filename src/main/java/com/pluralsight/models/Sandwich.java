package com.pluralsight.models;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.enums.BreadType;
import com.pluralsight.toppings.Topping;
import com.pluralsight.interfaces.Pricable;

import java.math.BigDecimal;
import java.util.*;

// Sandwich is a concrete Pricable that holds toppings, premium extras and uses PricingService.
public class Sandwich implements Pricable {
    private final SandwichSize size;
    private final BreadType bread;
    private final List<Topping> regularToppings = new ArrayList<>();
    private final Map<Topping, Integer> premiumToppings = new LinkedHashMap<>(); // count indicates how many units (1 default)
    private final Map<Topping, Integer> premiumExtraCounts = new HashMap<>(); // extra counts per premium topping
    private boolean toasted = false;

    public Sandwich(SandwichSize size, BreadType bread) {
        this.size = Objects.requireNonNull(size);
        this.bread = Objects.requireNonNull(bread);
    }

    // Add a regular topping
    public void addRegularTopping(Topping topping) {
        if (topping == null) return;
        regularToppings.add(topping);
    }

    // Add a premium topping with quantity (default 1)
    public void addPremiumTopping(Topping topping, int quantity, int extraCount) {
        if (topping == null) return;
        premiumToppings.put(topping, premiumToppings.getOrDefault(topping, 0) + quantity);
        premiumExtraCounts.put(topping, premiumExtraCounts.getOrDefault(topping, 0) + Math.max(0, extraCount));
    }

    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }

    public boolean isToasted() {
        return toasted;
    }

    public SandwichSize getSize() {
        return size;
    }

    public BreadType getBread() {
        return bread;
    }

    public List<Topping> getRegularToppings() {
        return Collections.unmodifiableList(regularToppings);
    }

    public Map<Topping, Integer> getPremiumToppings() {
        return Collections.unmodifiableMap(premiumToppings);
    }

    public Map<Topping, Integer> getPremiumExtraCounts() {
        return Collections.unmodifiableMap(premiumExtraCounts);
    }

    @Override
    public BigDecimal getPrice() {
        SandwichPriceCalculator calculator = new SandwichPriceCalculator(size, premiumToppings, premiumExtraCounts);
        return calculator.calculate();
    }

    @Override
    public String toReceiptText() {
        SandwichReceiptFormatter formatter = new SandwichReceiptFormatter(this);
        return formatter.format();
    }

    @Override
    public String toString() {
        return toReceiptText();
    }
}

