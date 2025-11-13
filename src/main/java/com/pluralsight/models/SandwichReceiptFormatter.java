package com.pluralsight.models;

import com.pluralsight.toppings.Topping;
import com.pluralsight.services.PricingService;
import java.math.BigDecimal;
import java.util.stream.Collectors;

// Helper class to format sandwich receipt text and separate formatting logic from Sandwich.
public class SandwichReceiptFormatter {
    private final Sandwich sandwich;
    
    public SandwichReceiptFormatter(Sandwich sandwich) {
        this.sandwich = sandwich;
    }
    
    // Format receipt text using streams and lambdas
    public String format() {
        StringBuilder sb = new StringBuilder(256);
        appendHeader(sb);
        appendPremiumToppings(sb);
        appendRegularToppings(sb);
        appendPrice(sb);
        return sb.toString();
    }
    
    private void appendHeader(StringBuilder sb) {
        sb.append(String.format("%s %s, %s\n", 
                sandwich.getSize().toDisplay(), 
                sandwich.getBread().name(), 
                sandwich.isToasted() ? "Toasted" : "Not toasted"));
    }
    
    private void appendPremiumToppings(StringBuilder sb) {
        sandwich.getPremiumToppings().forEach((topping, qty) -> {
            BigDecimal unitPrice = topping.getPriceBySize(sandwich.getSize());
            BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2, java.math.RoundingMode.HALF_UP);
            sb.append(String.format("  - %s x%d => $%s\n", topping.getName(), qty, total.toPlainString()));
            
            int extras = sandwich.getPremiumExtraCounts().getOrDefault(topping, 0);
            if (extras > 0) {
                appendExtrasToppings(sb, topping, extras);
            }
        });
    }
    
    private void appendExtrasToppings(StringBuilder sb, Topping topping, int extras) {
        BigDecimal extraUnit = getExtraPrice(topping);
        BigDecimal extraTotal = extraUnit.multiply(new BigDecimal(extras)).setScale(2, java.math.RoundingMode.HALF_UP);
        sb.append(String.format("    Extra %s x%d => $%s\n", topping.getName(), extras, extraTotal.toPlainString()));
    }
    
    private void appendRegularToppings(StringBuilder sb) {
        if (!sandwich.getRegularToppings().isEmpty()) {
            String toppings = sandwich.getRegularToppings().stream()
                    .map(Topping::getName)
                    .collect(Collectors.joining(", "));
            sb.append("  - Regular: ").append(toppings).append("\n");
        }
    }
    
    private void appendPrice(StringBuilder sb) {
        sb.append(String.format("  Price: $%s\n", sandwich.getPrice().toPlainString()));
    }
    
    // Demonstrates instanceof usage
    private BigDecimal getExtraPrice(Topping topping) {
        if (topping instanceof com.pluralsight.toppings.Meat) {
            return PricingService.getExtraMeatPrice(sandwich.getSize());
        } else if (topping instanceof com.pluralsight.toppings.Cheese) {
            return PricingService.getExtraCheesePrice(sandwich.getSize());
        }
        return BigDecimal.ZERO;
    }
}
