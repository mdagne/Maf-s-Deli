package com.pluralsight.services;

import com.pluralsight.models.Order;
import com.pluralsight.models.Sandwich;
import com.pluralsight.sides.Drink;
import com.pluralsight.sides.Chips;

import java.io.IOException;
import java.math.BigDecimal;

// Manages the current order, adds items, provides summaries, and handles checkout.
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

    public OrderSummary getOrderSummary() {
        int sandwichCount = currentOrder.getSandwiches().size();
        int drinkCount = currentOrder.getDrinks().size();
        int chipsCount = currentOrder.getChips().size();
        BigDecimal total = currentOrder.calculateSubtotal();
        
        return new OrderSummary(sandwichCount, drinkCount, chipsCount, total);
    }
    
    public static class OrderSummary {
        private final int sandwichCount;
        private final int drinkCount;
        private final int chipsCount;
        private final java.math.BigDecimal total;
        
        public OrderSummary(int sandwichCount, int drinkCount, int chipsCount, java.math.BigDecimal total) {
            this.sandwichCount = sandwichCount;
            this.drinkCount = drinkCount;
            this.chipsCount = chipsCount;
            this.total = total;
        }
        
        public boolean isEmpty() {
            return sandwichCount == 0 && drinkCount == 0 && chipsCount == 0;
        }
        
        public int getSandwichCount() { return sandwichCount; }
        public int getDrinkCount() { return drinkCount; }
        public int getChipsCount() { return chipsCount; }
        public java.math.BigDecimal getTotal() { return total; }
    }

    public String checkout() {
        if (!currentOrder.isValid()) {
            System.out.println("Invalid order: If you have 0 sandwiches, you must add chips or a drink.");
            return null;
        }

        try {
            String receiptPath = currentOrder.saveReceipt();
            startNewOrder();
            return receiptPath;
        } catch (IOException e) {
            System.err.println("Failed to save receipt: " + e.getMessage());
            return null;
        }
    }

    public boolean canCheckout() {
        return currentOrder.isValid();
    }
}
