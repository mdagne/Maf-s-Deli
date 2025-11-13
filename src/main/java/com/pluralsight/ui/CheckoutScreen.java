package com.pluralsight.ui;

import com.pluralsight.services.OrderManager;
import com.pluralsight.utils.MenuUtils;
import com.pluralsight.enums.PaymentMethod;

public class CheckoutScreen {

    private final OrderManager orderManager;

    public CheckoutScreen(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void display() {
        System.out.println("\n=== Checkout ===");
        
        // Validate order
        if (!orderManager.canCheckout()) {
            System.out.println("Invalid order: If you have 0 sandwiches, you must add chips or a drink.");
            return;
        }
        
        // Display order summary
        orderManager.displayOrderSummary();

        // Ask for payment method
        PaymentMethod paymentMethod = MenuUtils.choosePaymentMethod();
        orderManager.getCurrentOrder().setPaymentMethod(paymentMethod);

        boolean confirm = MenuUtils.readYesNo("\nWould you like to confirm your order? (y/n): ");
        if (confirm) {
            System.out.println("Payment method: " + paymentMethod.getDisplayName());
            if (orderManager.checkout()) {
                System.out.println("Order confirmed and receipt saved!");
            } else {
                System.out.println("Failed to save receipt. Order not completed.");
            }
        } else {
            System.out.println("Checkout cancelled. Returning to order menu...");
        }
    }
}
