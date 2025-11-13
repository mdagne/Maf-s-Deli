package com.pluralsight.utils;

import com.pluralsight.toppings.Cheese;
import java.util.List;

// Concrete strategy for cheese selection
public class CheeseSelectionStrategy implements MenuSelectionStrategy {
    @Override
    public List<String> getAvailableOptions() {
        return Cheese.getAvailableCheeses();
    }
    
    @Override
    public String getItemTypeName() {
        return "cheese";
    }
}
