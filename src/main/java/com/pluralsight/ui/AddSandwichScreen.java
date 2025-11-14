package com.pluralsight.ui;

import com.pluralsight.models.Sandwich;
import com.pluralsight.enums.BreadType;
import com.pluralsight.enums.SandwichSize;
import com.pluralsight.services.OrderManager;
import com.pluralsight.toppings.Cheese;
import com.pluralsight.toppings.Meat;
import com.pluralsight.toppings.Topping;
import com.pluralsight.toppings.Vegetable;
import com.pluralsight.toppings.Sauce;
import com.pluralsight.utils.MenuUtils;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

// AddSandwichScreen guides users through selecting size, bread, toppings, and extras to build a custom sandwich.
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
        selectMeatsWithExtras(sandwich);
        // Select cheeses
        System.out.println("\n--- Select Cheeses ---");
        selectCheesesWithExtras(sandwich);
        // Select regular toppings (vegetables)
        System.out.println("\n--- Select Regular Toppings ---");
        List<String> selectedToppings = MenuUtils.chooseRegularToppings();
        selectedToppings.forEach(toppingName -> sandwich.addRegularTopping(new Vegetable(toppingName)));
        // Select sauces
        System.out.println("\n--- Select Sauces ---");
        addSauces(sandwich, MenuUtils.chooseSauces());
        // Select dipping sauces
        System.out.println("\n--- Select Dipping Sauces ---");
        addSauces(sandwich, MenuUtils.chooseSides());
        // Toasted?
        boolean toasted = MenuUtils.readYesNo("\nWould you like the sandwich toasted? (y/n): ");
        sandwich.setToasted(toasted);
        // Add sandwich to order
        orderManager.addSandwich(sandwich);
        System.out.println("\nSandwich added successfully!");
        System.out.println("Sandwich price: $" + sandwich.getPrice().toPlainString());
    }

    private void selectMeatsWithExtras(Sandwich sandwich) {
        selectPremiumToppingWithExtras(
            sandwich,
            Meat.getAvailableMeats(),
            "meat",
            name -> new Meat(name)
        );
    }

    private void selectCheesesWithExtras(Sandwich sandwich) {
        selectPremiumToppingWithExtras(
            sandwich,
            Cheese.getAvailableCheeses(),
            "cheese",
            name -> new Cheese(name)
        );
    }

    @FunctionalInterface
    private interface ToppingFactory {
        Topping create(String name);
    }

    private void selectPremiumToppingWithExtras(Sandwich sandwich, List<String> availableItems, 
                                                  String itemType, ToppingFactory factory) {
        System.out.println("\nAvailable " + itemType + "s:");
        for (int i = 0; i < availableItems.size(); i++) {
            System.out.println((i + 1) + ") " + availableItems.get(i));
        }
        System.out.println("0) No " + itemType);

        Set<Integer> selectedIndices = new HashSet<>();
        while (true) {
            int choice = MenuUtils.readInt("Enter your choice (0 for no " + itemType + "): ");
            if (choice == 0) {
                break;
            }
            if (choice < 1 || choice > availableItems.size()) {
                System.out.println("Invalid choice. Please enter a number between 1 and " + availableItems.size() + ", or 0 for no " + itemType + ".");
                continue;
            }
            int index = choice - 1;
            String itemName = availableItems.get(index);
            if (selectedIndices.contains(index)) {
                System.out.println(itemName + " is already added to your sandwich.");
            } else {
                selectedIndices.add(index);
                System.out.println(itemName + " added.");
                int extraCount = MenuUtils.readExtraCount(itemName, itemType);
                sandwich.addPremiumTopping(factory.create(itemName), 1, extraCount);
            }
        }
    }

    // Adds sauce toppings (regular or dipping) to sandwich
    private void addSauces(Sandwich sandwich, List<String> sauceNames) {
        sauceNames.forEach(name -> sandwich.addRegularTopping(new Sauce(name)));
    }
}
