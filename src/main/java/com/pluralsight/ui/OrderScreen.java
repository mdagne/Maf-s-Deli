package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;

// OrderScreen displays the order menu to add items, view summary, checkout, or cancel order.
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
                case 2 -> new AddSignatureSandwichScreen(orderManager).display();
                case 3 -> new AddDrinkScreen(orderManager).display();
                case 4 -> new AddChipsScreen(orderManager).display();
                case 5 -> {
                    // Process checkout - if order is completed, return to home screen
                    new CheckoutScreen(orderManager).display();
                    Order updatedOrder = orderManager.getCurrentOrder();
                    if (updatedOrder.getSandwiches().isEmpty() 
                        && updatedOrder.getDrinks().isEmpty()
                        && updatedOrder.getChips().isEmpty()) {
                        // Order was completed and reset, return to home
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
        System.out.println("2) Add Signature Sandwich");
        System.out.println("3) Add Drink");
        System.out.println("4) Add Chips");
        System.out.println("5) Checkout");
        System.out.println("0) Cancel Order");
    }
    
    private void displayOrderSummary() {
        OrderManager.OrderSummary summary = orderManager.getOrderSummary();
        
        if (summary.isEmpty()) {
            System.out.println("\nCurrent order is empty.");
        } else {
            System.out.println("\nCurrent order: " + summary.getSandwichCount() + " sandwich(es), " 
                    + summary.getDrinkCount() + " drink(s), " + summary.getChipsCount() + " chip(s)");
            System.out.println("Current total: $" + summary.getTotal().toPlainString());
        }
    }
}
