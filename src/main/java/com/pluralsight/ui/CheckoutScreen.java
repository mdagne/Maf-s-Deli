package com.pluralsight.ui;

import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;
import com.pluralsight.enums.PaymentMethod;

// CheckoutScreen displays order summary, collects payment method, confirms order, and saves receipt.
public class CheckoutScreen {
    private static final String INVALID_ORDER_MESSAGE = 
            "Invalid order: If you have 0 sandwiches, you must add chips or a drink.";
    
    private final OrderManager orderManager;

    public CheckoutScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Checkout ===");
        
        // Validate order
        if (!orderManager.canCheckout()) {
            System.out.println(INVALID_ORDER_MESSAGE);
            return;
        }
        
        // Display order summary
        OrderManager.OrderSummary summary = orderManager.getOrderSummary();
        if (summary.isEmpty()) {
            System.out.println("\nYour order is empty.");
        } else {
            System.out.println("\n=== Order Summary ===");
            System.out.println("Items: " + summary.getSandwichCount() + " sandwich(es), " 
                    + summary.getDrinkCount() + " drink(s), " + summary.getChipsCount() + " chip(s)");
            System.out.println("Total: $" + summary.getTotal().toPlainString());
        }

        // Ask for payment method
        PaymentMethod paymentMethod = MenuUtils.choosePaymentMethod();
        orderManager.getCurrentOrder().setPaymentMethod(paymentMethod);

        boolean confirm = MenuUtils.readYesNo("\nWould you like to confirm your order? (y/n): ");
        if (confirm) {
            System.out.println("Payment method: " + paymentMethod.getDisplayName());
            String receiptPath = orderManager.checkout();
            if (receiptPath != null) {
                System.out.println("\nOrder confirmed!");
            } else {
                System.out.println("Failed to save receipt. Order not completed.");
            }
        } else {
            System.out.println("Checkout cancelled. Returning to order menu...");
        }
    }
}
