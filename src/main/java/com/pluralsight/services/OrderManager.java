package com.pluralsight.services;

import com.pluralsight.models.Order;
import com.pluralsight.models.Sandwich;
import com.pluralsight.sides.Drink;
import com.pluralsight.sides.Chips;

import java.io.IOException;
import java.math.BigDecimal;

// Manages the current order and handles checkout
public class OrderManager {
    private static final String INVALID_ORDER_MESSAGE =
            "Invalid order: If you have 0 sandwiches, you must add chips or a drink.";

    private Order currentOrder;

    public OrderManager() {
        currentOrder = null;
    }

    // Starts a new order with specified customer name
    public void startNewOrder(String customerName) {
        currentOrder = new Order(customerName);
    }

    // Starts a new order with default Guest name
    public void startNewOrder() {
        startNewOrder("Guest");
    }

    // Cancels current order and starts a new empty one
    public void cancelOrder() {
        startNewOrder();
    }

    // Returns current order, creating default one if none exists
    public Order getCurrentOrder() {
        if (currentOrder == null) {
            currentOrder = new Order("Guest");
        }
        return currentOrder;
    }

    public void addSandwich(Sandwich sandwich) {
        getCurrentOrder().addSandwich(sandwich);
    }

    public void addDrink(Drink drink) {
        getCurrentOrder().addDrink(drink);
    }

    public void addChips(Chips chips) {
        getCurrentOrder().addChips(chips);
    }

    // Returns summary with item counts and total price
    public OrderSummary getOrderSummary() {
        Order order = getCurrentOrder();
        int sandwichCount = order.getSandwiches().size();
        int drinkCount = order.getDrinks().size();
        int chipsCount = order.getChips().size();
        BigDecimal total = order.calculateSubtotal();

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

        // Returns true if order has no items
        public boolean isEmpty() {
            return sandwichCount == 0 && drinkCount == 0 && chipsCount == 0;
        }

        public int getSandwichCount() { return sandwichCount; }
        public int getDrinkCount() { return drinkCount; }
        public int getChipsCount() { return chipsCount; }
        public java.math.BigDecimal getTotal() { return total; }
    }

    // Processes checkout: saves receipt and starts new order
    public String checkout() {
        Order order = getCurrentOrder();
        if (!order.isValid()) {
            System.out.println(INVALID_ORDER_MESSAGE);
            return null;
        }

        try {
            String receiptPath = order.saveReceipt();
            startNewOrder();
            return receiptPath;
        } catch (IOException e) {
            System.err.println("Failed to save receipt: " + e.getMessage());
            return null;
        }
    }

    public boolean canCheckout() {
        return getCurrentOrder().isValid();
    }
}
