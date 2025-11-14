package com.pluralsight.ui;

import com.pluralsight.models.SignatureSandwich;
import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;
// AddSignatureSandwichScreen allows users to select and add predefined signature sandwiches to their order.
public class AddSignatureSandwichScreen {
    private final OrderManager orderManager;

    public AddSignatureSandwichScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Add Signature Sandwich ===");
        System.out.println("\nAvailable signature sandwiches:");
        System.out.println("1) BLT");
        System.out.println("2) Philly Cheese Steak");
        
        while (true) {
            int choice = MenuUtils.readInt("Enter your choice: ");
            
            SignatureSandwich signatureSandwich = null;
            switch (choice) {
                case 1:
                    signatureSandwich = SignatureSandwich.createBLT();
                    break;
                case 2:
                    signatureSandwich = SignatureSandwich.createPhillyCheeseSteak();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                    continue;
            }
            // Add signature sandwich to order
            orderManager.addSandwich(signatureSandwich);
            System.out.println("\nSignature sandwich added successfully!");
            System.out.println("Sandwich: " + signatureSandwich.getSignatureName());
            System.out.println("Sandwich price: $" + signatureSandwich.getPrice().toPlainString());
            return;
        }
    }
}

