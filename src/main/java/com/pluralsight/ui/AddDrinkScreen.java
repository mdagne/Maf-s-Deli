package com.pluralsight.ui;

import com.pluralsight.sides.Drink;
import com.pluralsight.enums.DrinkSize;
import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

public class AddDrinkScreen {

    private final OrderManager orderManager;

    public AddDrinkScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Add Drink ===");
        String drinkFlavor = MenuUtils.chooseDrink();
        DrinkSize size = MenuUtils.chooseDrinkSize();
        
        Drink drink = new Drink(drinkFlavor, size);
        orderManager.addDrink(drink);
        System.out.println("Drink added successfully!");
        System.out.println("Drink price: $" + drink.getPrice().toPlainString());
    }
}
