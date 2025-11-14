package com.pluralsight.ui;

import com.pluralsight.sides.Chips;
import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

import java.util.List;

// AddChipsScreen displays available chip types and adds the selected chips to the order.
public class AddChipsScreen {

    private final OrderManager orderManager;

    public AddChipsScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Add Chips ===");
        List<String> chipTypes = Chips.getAvailableChips();
        System.out.println("Available chips:");
        for (int i = 0; i < chipTypes.size(); i++) {
            System.out.println((i + 1) + ") " + chipTypes.get(i));
        }
        System.out.println("0) Skip/No chips");

        int choice = MenuUtils.readInt("Enter your choice (0 to skip): ");
        if (choice == 0) {
            System.out.println("No chips added.");
            return;
        }
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
