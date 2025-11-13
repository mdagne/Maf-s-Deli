package com.pluralsight.services;

import com.pluralsight.models.Order;
import com.pluralsight.models.Sandwich;
import com.pluralsight.sides.Drink;
import com.pluralsight.sides.Chips;

import java.io.IOException;

// OrderManager manages current order state and provides methods for UI screens to interact with orders.
public class OrderManager {
    private Order currentOrder;

    public OrderManager() {
        currentOrder = new Order("Guest");
    }

    // Start a new order with customer name
    public void startNewOrder(String customerName) {
        currentOrder = new Order(customerName);
    }

    // Start a new order with default name
    public void startNewOrder() {
        startNewOrder("Guest");
    }

    public void cancelOrder() {
        startNewOrder();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void addSandwich(Sandwich sandwich) {
        currentOrder.addSandwich(sandwich);
    }

    public void addDrink(Drink drink) {
        currentOrder.addDrink(drink);
    }

    public void addChips(Chips chips) {
        currentOrder.addChips(chips);
    }

    public void displayOrderSummary() {
        boolean isEmpty = currentOrder.getSandwiches().isEmpty() 
            && currentOrder.getDrinks().isEmpty() 
            && currentOrder.getChips().isEmpty();
            
        if (isEmpty) {
            System.out.println("\nYour order is empty.");
            return;
        }

        System.out.println("\n=== Order Summary ===");
        System.out.println(currentOrder.buildReceiptText());
        System.out.println("Total: $" + currentOrder.calculateSubtotal().toPlainString());
    }

    public boolean checkout() {
        if (!currentOrder.isValid()) {
            System.out.println("Invalid order: If you have 0 sandwiches, you must add chips or a drink.");
            return false;
        }

        try {
            currentOrder.saveReceipt();
            startNewOrder(); // Start a new order after checkout
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save receipt: " + e.getMessage());
            return false;
        }
    }

    public boolean canCheckout() {
        return currentOrder.isValid();
    }
}
