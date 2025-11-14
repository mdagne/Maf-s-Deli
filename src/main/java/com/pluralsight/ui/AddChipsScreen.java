package com.pluralsight.ui;

import com.pluralsight.sides.Chips;
import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

// AddChipsScreen displays available chip types and adds the selected chips to the order.
public class AddChipsScreen {

    private final OrderManager orderManager;

    public AddChipsScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Add Chips ===");
        // Let user choose from available chips
        java.util.List<String> chipTypes = Chips.getAvailableChips();
        System.out.println("Available chips:");
        for (int i = 0; i < chipTypes.size(); i++) {
            System.out.println((i + 1) + ") " + chipTypes.get(i));
        }
        
        int choice = MenuUtils.readInt("Enter your choice: ");
        if (choice < 1 || choice > chipTypes.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        
        Chips chips = new Chips(chipTypes.get(choice - 1));
        orderManager.addChips(chips);
        System.out.println("Chips added successfully!");
        System.out.println("Chips price: $" + chips.getPrice().toPlainString());
    }
}
