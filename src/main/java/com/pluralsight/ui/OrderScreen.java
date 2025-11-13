package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

// OrderScreen extends UIScreen, demonstrating inheritance and polymorphism.
public class OrderScreen extends UIScreen {

    private final OrderManager orderManager;

    public OrderScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @Override
    protected void renderHeader() {
        Order currentOrder = orderManager.getCurrentOrder();
        System.out.println("\n=== Order Menu ===");
        System.out.println("Customer: " + currentOrder.getCustomerName());
    }

    @Override
    protected void renderContent() {
        while (true) {
            displayMenuOptions();
            displayOrderSummary();
            
            int choice = MenuUtils.readInt("\nEnter your choice: ");

            switch (choice) {
                case 1 -> new AddSandwichScreen(orderManager).display();
                case 2 -> new AddDrinkScreen(orderManager).display();
                case 3 -> new AddChipsScreen(orderManager).display();
                case 4 -> {
                    new CheckoutScreen(orderManager).display();
                    Order updatedOrder = orderManager.getCurrentOrder();
                    if (updatedOrder.getSandwiches().isEmpty() 
                        && updatedOrder.getDrinks().isEmpty()
                        && updatedOrder.getChips().isEmpty()) {
                        return;
                    }
                }
                case 0 -> {
                    orderManager.cancelOrder();
                    System.out.println("Order cancelled. Returning to home screen...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMenuOptions() {
        System.out.println("1) Add Sandwich");
        System.out.println("2) Add Drink");
        System.out.println("3) Add Chips");
        System.out.println("4) Checkout");
        System.out.println("0) Cancel Order");
    }
    
    private void displayOrderSummary() {
        Order currentOrder = orderManager.getCurrentOrder();
        int sandwichCount = currentOrder.getSandwiches().size();
        int drinkCount = currentOrder.getDrinks().size();
        int chipsCount = currentOrder.getChips().size();
        
        if (sandwichCount > 0 || drinkCount > 0 || chipsCount > 0) {
            System.out.println("\nCurrent order: " + sandwichCount + " sandwich(es), " 
                    + drinkCount + " drink(s), " + chipsCount + " chip(s)");
            System.out.println("Current total: $" + currentOrder.calculateSubtotal().toPlainString());
        } else {
            System.out.println("\nCurrent order is empty.");
        }
    }
}
