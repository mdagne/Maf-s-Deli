package com.pluralsight.models;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.enums.BreadType;
import com.pluralsight.toppings.Topping;
import com.pluralsight.toppings.Meat;
import com.pluralsight.toppings.Cheese;
import com.pluralsight.interfaces.Pricable;
import com.pluralsight.services.PricingService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Objects;

// Sandwich implements Pricable interface
public class Sandwich implements Pricable {
    private static final int PRICE_DECIMAL_PLACES = 2;
    private static final int RECEIPT_BUFFER_SIZE = 256;

    private final SandwichSize size;
    private final BreadType bread;
    private final List<Topping> regularToppings = new ArrayList<>();
    private final Map<Topping, Integer> premiumToppings = new LinkedHashMap<>();
    private final Map<Topping, Integer> premiumExtraCounts = new HashMap<>();
    private boolean toasted = false;

    public Sandwich(SandwichSize size, BreadType bread) {
        this.size = Objects.requireNonNull(size);
        this.bread = Objects.requireNonNull(bread);
    }

    // Adds a free regular topping to the sandwich
    public void addRegularTopping(Topping topping) {
        if (topping == null) return;
        regularToppings.add(topping);
    }

    // Adds a premium topping with quantity and extra portions
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

    // Calculates total price including base bread cost and all premium toppings
    @Override
    public BigDecimal getPrice() {
        BigDecimal basePrice = PricingService.getBreadPrice(size);

        BigDecimal premiumsTotal = premiumToppings.entrySet().stream()
                .map(entry -> {
                    Topping topping = entry.getKey();
                    int quantity = entry.getValue();
                    BigDecimal unitPrice = topping.getPriceBySize(size);
                    BigDecimal standardTotal = unitPrice.multiply(new BigDecimal(quantity));

                    int extras = premiumExtraCounts.getOrDefault(topping, 0);
                    if (extras > 0) {
                        BigDecimal extraUnitPrice = getExtraPriceForTopping(topping);
                        BigDecimal extraTotal = extraUnitPrice.multiply(new BigDecimal(extras));
                        standardTotal = standardTotal.add(extraTotal);
                    }

                    return standardTotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return basePrice.add(premiumsTotal).setScale(PRICE_DECIMAL_PLACES, java.math.RoundingMode.HALF_UP);
    }

    // Generates formatted receipt text with size, bread, toppings, and price
    @Override
    public String toReceiptText() {
        StringBuilder sb = new StringBuilder(RECEIPT_BUFFER_SIZE);

        sb.append(String.format("%s %s, %s\n",
                size.toDisplay(),
                bread.name(),
                toasted ? "Toasted" : "Not toasted"));

        premiumToppings.forEach((topping, quantity) -> {
            BigDecimal unitPrice = topping.getPriceBySize(size);
            BigDecimal total = unitPrice.multiply(new BigDecimal(quantity))
                    .setScale(PRICE_DECIMAL_PLACES, java.math.RoundingMode.HALF_UP);
            sb.append(String.format("  - %s x%d => $%s\n", topping.getName(), quantity, total.toPlainString()));

            int extras = premiumExtraCounts.getOrDefault(topping, 0);
            if (extras > 0) {
                BigDecimal extraUnitPrice = getExtraPriceForTopping(topping);
                BigDecimal extraTotal = extraUnitPrice.multiply(new BigDecimal(extras))
                        .setScale(PRICE_DECIMAL_PLACES, java.math.RoundingMode.HALF_UP);
                sb.append(String.format("    Extra %s x%d => $%s\n", topping.getName(), extras, extraTotal.toPlainString()));
            }
        });
        
        if (!regularToppings.isEmpty()) {
            String toppingsList = regularToppings.stream()
                    .map(Topping::getName)
                    .collect(Collectors.joining(", "));
            sb.append("  - Regular: ").append(toppingsList).append("\n");
        }
        
        sb.append(String.format("  Price: $%s\n", getPrice().toPlainString()));
        return sb.toString();
    }

    // Uses instanceof to get extra price for Meat or Cheese toppings
    private BigDecimal getExtraPriceForTopping(Topping topping) {
        if (topping instanceof Meat) {
            return PricingService.getExtraMeatPrice(size);
        } else if (topping instanceof Cheese) {
            return PricingService.getExtraCheesePrice(size);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return toReceiptText();
    }
}

