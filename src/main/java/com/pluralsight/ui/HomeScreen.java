package com.pluralsight.ui;

import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

/**
 * HomeScreen extends UIScreen, demonstrating inheritance.
 */
public class HomeScreen extends UIScreen {

    private final OrderManager orderManager;

    public HomeScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @Override
    protected void renderHeader() {
        System.out.println("\n=== Welcome to DELI-cious ===");
    }

    @Override
    protected void renderContent() {
        while (true) {
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            int choice = MenuUtils.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    String customerName = MenuUtils.readString("Enter customer name: ");
                    orderManager.startNewOrder(customerName);
                    new OrderScreen(orderManager).display();
                }
                case 0 -> {
                    System.out.println("Thank you for visiting DELI-cious!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
