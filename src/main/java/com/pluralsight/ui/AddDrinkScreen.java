package com.pluralsight.ui;

import com.pluralsight.sides.Drink;
import com.pluralsight.enums.DrinkSize;
import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

// AddDrinkScreen prompts users to select a drink flavor and size, then adds it to the order.
public class AddDrinkScreen {

    private final OrderManager orderManager;

    public AddDrinkScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Add Drink ===");
        String drinkFlavor = MenuUtils.chooseDrink();
        
        if (drinkFlavor == null) {
            System.out.println("No drink added.");
            return;
        }
        DrinkSize size = MenuUtils.chooseDrinkSize();
        
        Drink drink = new Drink(drinkFlavor, size);
        orderManager.addDrink(drink);
        System.out.println("Drink added successfully!");
        System.out.println("Drink price: $" + drink.getPrice().toPlainString());
    }
}
