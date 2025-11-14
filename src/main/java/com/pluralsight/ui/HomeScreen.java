package com.pluralsight.ui;

import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

// HomeScreen displays the main menu to start a new order or exit the application.
public class HomeScreen extends UIScreen {

    private final OrderManager orderManager;

    public HomeScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @Override
    protected void renderHeader() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        🥪  WELCOME TO MAF'S DELI  🥪");
        System.out.println("=".repeat(50));
        System.out.println();
        System.out.println("            ╭─────────╮");
        System.out.println("           ╱           ╲");
        System.out.println("          │  🥪 SANDWICH │");
        System.out.println("           ╲           ╱");
        System.out.println("            ╰─────────╯");
        System.out.println();
        System.out.println("      Fresh Sandwiches Made to Order");
        System.out.println("=".repeat(50));
        System.out.println();
    }

    @Override
    protected void renderContent() {
        while (true) {
            System.out.println("MAIN MENU");
            System.out.println("-".repeat(30));
            System.out.println("1)  Start New Order");
            System.out.println("0)  Exit Application");
            System.out.println();
            
            int choice = MenuUtils.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    System.out.println();
                    String customerName = MenuUtils.readString("Enter customer name: ");
                    if (customerName == null || customerName.trim().isEmpty()) {
                        customerName = "Guest";
                    }
                    orderManager.startNewOrder(customerName);
                    System.out.println("\n✓ Order started for " + customerName + "!\n");
                    new OrderScreen(orderManager).display();
                }
                case 0 -> {
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("  Thank you for visiting Maf's Deli!");
                    System.out.println("         Have a great day! 👋");
                    System.out.println("=".repeat(50) + "\n");
                    return;
                }
                default -> {
                    System.out.println("\n⚠ Invalid choice. Please try again.\n");
                }
            }
        }
    }
}
