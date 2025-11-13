package com.pluralsight.ui;

import com.pluralsight.models.Sandwich;
import com.pluralsight.enums.BreadType;
import com.pluralsight.enums.SandwichSize;
import com.pluralsight.services.OrderManager;
import com.pluralsight.toppings.Cheese;
import com.pluralsight.toppings.Meat;
import com.pluralsight.toppings.Vegetable;
import com.pluralsight.toppings.Sauce;
import com.pluralsight.utils.MenuUtils;

import java.util.List;

public class AddSandwichScreen {

    private final OrderManager orderManager;

    public AddSandwichScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Add Sandwich ===");

        // Select size
        SandwichSize size = MenuUtils.chooseSandwichSize();
        
        // Select bread
        BreadType bread = MenuUtils.chooseBreadType();
        
        // Create sandwich
        Sandwich sandwich = new Sandwich(size, bread);
        
        // Select meats
        System.out.println("\n--- Select Meats ---");
        List<String> selectedMeats = MenuUtils.chooseMeats();
        if (!selectedMeats.isEmpty()) {
            for (String meatName : selectedMeats) {
                int extraCount = MenuUtils.readExtraCount(meatName, "meat");
                sandwich.addPremiumTopping(new Meat(meatName), 1, extraCount);
            }
        }
        
        // Select cheeses
        System.out.println("\n--- Select Cheeses ---");
        List<String> selectedCheeses = MenuUtils.chooseCheeses();
        if (!selectedCheeses.isEmpty()) {
            for (String cheeseName : selectedCheeses) {
                int extraCount = MenuUtils.readExtraCount(cheeseName, "cheese");
                sandwich.addPremiumTopping(new Cheese(cheeseName), 1, extraCount);
            }
        }
        
        // Select regular toppings (vegetables)
        System.out.println("\n--- Select Regular Toppings ---");
        List<String> selectedToppings = MenuUtils.chooseRegularToppings();
        for (String toppingName : selectedToppings) {
            sandwich.addRegularTopping(new Vegetable(toppingName));
        }
        
        // Select sauces
        System.out.println("\n--- Select Sauces ---");
        List<String> selectedSauces = MenuUtils.chooseSauces();
        for (String sauceName : selectedSauces) {
            sandwich.addRegularTopping(new Sauce(sauceName));
        }
        
        // Select sides (like au jus) - these are also sauces
        System.out.println("\n--- Select Sides ---");
        List<String> selectedSides = MenuUtils.chooseSides();
        for (String sideName : selectedSides) {
            sandwich.addRegularTopping(new Sauce(sideName));
        }
        
        // Toasted?
        boolean toasted = MenuUtils.readYesNo("\nWould you like the sandwich toasted? (y/n): ");
        sandwich.setToasted(toasted);
        
        // Add sandwich to order
        orderManager.addSandwich(sandwich);
        System.out.println("\nSandwich added successfully!");
        System.out.println("Sandwich price: $" + sandwich.getPrice().toPlainString());
    }
}
