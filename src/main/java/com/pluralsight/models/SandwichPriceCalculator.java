package com.pluralsight.models;

import com.pluralsight.toppings.Topping;
import com.pluralsight.enums.SandwichSize;
import com.pluralsight.services.PricingService;
import java.math.BigDecimal;
import java.util.*;

// Helper class to calculate sandwich prices and separate calculation concerns.
public class SandwichPriceCalculator {
    private final SandwichSize size;
    private final Map<Topping, Integer> premiumToppings;
    private final Map<Topping, Integer> premiumExtraCounts;
    
    public SandwichPriceCalculator(SandwichSize size, 
                                   Map<Topping, Integer> premiumToppings,
                                   Map<Topping, Integer> premiumExtraCounts) {
        this.size = size;
        this.premiumToppings = premiumToppings;
        this.premiumExtraCounts = premiumExtraCounts;
    }
    
    // Calculate total price using streams
    public BigDecimal calculate() {
        BigDecimal basePrice = PricingService.getBreadPrice(size);
        
        BigDecimal premiumsTotal = premiumToppings.entrySet().stream()
                .map(entry -> calculatePremiumPrice(entry.getKey(), entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return basePrice.add(premiumsTotal).setScale(2, java.math.RoundingMode.HALF_UP);
    }
    
    // Helper method for stream operation
    private BigDecimal calculatePremiumPrice(Topping topping, int quantity) {
        BigDecimal unitPrice = topping.getPriceBySize(size);
        BigDecimal baseTotal = unitPrice.multiply(new BigDecimal(quantity));
        
        int extras = premiumExtraCounts.getOrDefault(topping, 0);
        if (extras > 0) {
            BigDecimal extraUnitPrice = getExtraPrice(topping);
            baseTotal = baseTotal.add(extraUnitPrice.multiply(new BigDecimal(extras)));
        }
        
        return baseTotal;
    }
    
    // Demonstrates instanceof usage
    private BigDecimal getExtraPrice(Topping topping) {
        if (topping instanceof com.pluralsight.toppings.Meat) {
            return PricingService.getExtraMeatPrice(size);
        } else if (topping instanceof com.pluralsight.toppings.Cheese) {
            return PricingService.getExtraCheesePrice(size);
        }
        return BigDecimal.ZERO;
    }
}
