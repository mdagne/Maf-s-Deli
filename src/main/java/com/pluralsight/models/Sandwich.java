package com.pluralsight.models;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.enums.BreadType;
import com.pluralsight.toppings.Topping;
import com.pluralsight.interfaces.Pricable;
import com.pluralsight.services.PricingService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Objects;

// Sandwich manages toppings, calculates price based on size and extras, and formats receipt text.
public class Sandwich implements Pricable {
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

    public void addRegularTopping(Topping topping) {
        if (topping == null) return;
        regularToppings.add(topping);
    }

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
        BigDecimal basePrice = PricingService.getBreadPrice(size);
        
        BigDecimal premiumsTotal = premiumToppings.entrySet().stream()
                .map(entry -> {
                    Topping topping = entry.getKey();
                    int quantity = entry.getValue();
                    BigDecimal unitPrice = topping.getPriceBySize(size);
                    BigDecimal baseTotal = unitPrice.multiply(new BigDecimal(quantity));
                    
                    int extras = premiumExtraCounts.getOrDefault(topping, 0);
                    if (extras > 0) {
                        BigDecimal extraUnitPrice = topping.getExtraPrice(size);
                        baseTotal = baseTotal.add(extraUnitPrice.multiply(new BigDecimal(extras)));
                    }
                    
                    return baseTotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return basePrice.add(premiumsTotal).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    @Override
    public String toReceiptText() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(String.format("%s %s, %s\n", 
                size.toDisplay(), 
                bread.name(), 
                toasted ? "Toasted" : "Not toasted"));
        
        premiumToppings.forEach((topping, qty) -> {
            BigDecimal unitPrice = topping.getPriceBySize(size);
            BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2, java.math.RoundingMode.HALF_UP);
            sb.append(String.format("  - %s x%d => $%s\n", topping.getName(), qty, total.toPlainString()));
            
            int extras = premiumExtraCounts.getOrDefault(topping, 0);
            if (extras > 0) {
                BigDecimal extraUnit = topping.getExtraPrice(size);
                BigDecimal extraTotal = extraUnit.multiply(new BigDecimal(extras)).setScale(2, java.math.RoundingMode.HALF_UP);
                sb.append(String.format("    Extra %s x%d => $%s\n", topping.getName(), extras, extraTotal.toPlainString()));
            }
        });
        
        if (!regularToppings.isEmpty()) {
            String toppings = regularToppings.stream()
                    .map(Topping::getName)
                    .collect(Collectors.joining(", "));
            sb.append("  - Regular: ").append(toppings).append("\n");
        }
        
        sb.append(String.format("  Price: $%s\n", getPrice().toPlainString()));
        return sb.toString();
    }

    @Override
    public String toString() {
        return toReceiptText();
    }
}

