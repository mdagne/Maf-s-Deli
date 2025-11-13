package com.pluralsight.models;

import com.pluralsight.sides.Drink;
import com.pluralsight.sides.Chips;
import com.pluralsight.services.ReceiptService;
import com.pluralsight.enums.PaymentMethod;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Order aggregates sandwiches, drinks, and chips; newest entries are shown first using LinkedList.
public class Order {
    private final UUID id = UUID.randomUUID();
    private final LocalDateTime placedAt = LocalDateTime.now();
    private final String customerName;
    private PaymentMethod paymentMethod;
    private final LinkedList<Sandwich> sandwiches = new LinkedList<>();
    private final LinkedList<Drink> drinks = new LinkedList<>();
    private final LinkedList<Chips> chips = new LinkedList<>();

    // Constructor that accepts customer name
    public Order(String customerName) {
        this.customerName = customerName;
        this.paymentMethod = PaymentMethod.CASH; // Default payment method
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Newest first: addFirst
    public void addSandwich(Sandwich s) {
        sandwiches.addFirst(s);
    }

    public void addDrink(Drink d) {
        drinks.addFirst(d);
    }

    public void addChips(Chips c) {
        chips.addFirst(c);
    }

    public List<Sandwich> getSandwiches() {
        return Collections.unmodifiableList(sandwiches);
    }

    public List<Drink> getDrinks() {
        return Collections.unmodifiableList(drinks);
    }

    public List<Chips> getChips() {
        return Collections.unmodifiableList(chips);
    }

    public boolean isValid() {
        // If no sandwiches, require at least a drink or chips
        if (sandwiches.isEmpty()) {
            return !(drinks.isEmpty() && chips.isEmpty());
        }
        return true;
    }

    public BigDecimal calculateSubtotal() {
        // Using streams to calculate subtotal from all items
        BigDecimal total = sandwiches.stream()
                .map(Sandwich::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        total = total.add(drinks.stream()
                .map(Drink::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        
        total = total.add(chips.stream()
                .map(Chips::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        
        return total.setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public String buildReceiptText() {
        StringBuilder sb = new StringBuilder(512);
        sb.append("DELI-cious Receipt\n");
        sb.append("Customer: ").append(customerName).append("\n");
        sb.append("Order ID: ").append(id.toString()).append("\n");
        sb.append("Placed: ").append(placedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("--------------------------------\n");

        // Format sandwiches using streams
        int[] idx = {1};
        sandwiches.stream()
                .forEach(s -> {
                    sb.append("Sandwich ").append(idx[0]++).append(":\n");
                    sb.append(s.toReceiptText()).append("\n");
                });

        // Format drinks using stream if not empty
        if (!drinks.isEmpty()) {
            sb.append("Drinks:\n");
            drinks.stream()
                    .forEach(d -> sb.append("  - ").append(d.toReceiptText()).append("\n"));
            sb.append("\n");
        }

        // Format chips using stream if not empty
        if (!chips.isEmpty()) {
            sb.append("Chips:\n");
            chips.stream()
                    .forEach(c -> sb.append("  - ").append(c.toReceiptText()).append("\n"));
            sb.append("\n");
        }

        BigDecimal subtotal = calculateSubtotal();
        sb.append("--------------------------------\n");
        sb.append("Total: $").append(subtotal.toPlainString()).append("\n");
        sb.append("Payment Method: ").append(paymentMethod.getDisplayName()).append("\n");
        sb.append("--------------------------------\n");
        sb.append("Thank you for your order!\n");
        return sb.toString();
    }

    // Save receipt using ReceiptService and return filename
    public String saveReceipt() throws IOException {
        String content = buildReceiptText();
        return ReceiptService.saveReceipt(placedAt, content);
    }
}

