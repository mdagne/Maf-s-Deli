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
import java.util.concurrent.atomic.AtomicInteger;

// Order uses Pricable interface for different item types
public class Order {
    private static final int PRICE_DECIMAL_PLACES = 2;
    private static final int RECEIPT_BUFFER_SIZE = 512;
    private static final String RECEIPT_SEPARATOR = "--------------------------------";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static int idCounter = 1;
    private final int id = idCounter++;
    private final LocalDateTime placedAt = LocalDateTime.now();
    private final String customerName;
    private PaymentMethod paymentMethod;
    private final LinkedList<Sandwich> sandwiches = new LinkedList<>();
    private final LinkedList<Drink> drinks = new LinkedList<>();
    private final LinkedList<Chips> chips = new LinkedList<>();

    public Order(String customerName) {
        this.customerName = customerName;
        this.paymentMethod = PaymentMethod.CASH;
    }

    public int getId() {
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

    // Adds a sandwich to the order
    public void addSandwich(Sandwich sandwich) {
        sandwiches.addFirst(sandwich);
    }

    // Adds a drink to the order
    public void addDrink(Drink drink) {
        drinks.addFirst(drink);
    }

    // Adds chips to the order
    public void addChips(Chips chips) {
        this.chips.addFirst(chips);
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

    // Returns true if order has at least one item
    public boolean isValid() {
        return !sandwiches.isEmpty() || !drinks.isEmpty() || !chips.isEmpty();
    }

    // Calculates total price by summing all item prices
    public BigDecimal calculateSubtotal() {
        BigDecimal sandwichTotal = sandwiches.stream()
                .map(Sandwich::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal drinkTotal = drinks.stream()
                .map(Drink::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal chipsTotal = chips.stream()
                .map(Chips::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sandwichTotal.add(drinkTotal).add(chipsTotal)
                .setScale(PRICE_DECIMAL_PLACES, java.math.RoundingMode.HALF_UP);
    }

    // Builds formatted receipt text with all order details
    public String buildReceiptText() {
        StringBuilder sb = new StringBuilder(RECEIPT_BUFFER_SIZE);
        sb.append("Maf's Deli Receipt\n");
        sb.append("Customer: ").append(customerName).append("\n");
        sb.append("Order ID: ").append(id).append("\n");
        sb.append("Placed: ").append(placedAt.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))).append("\n");
        sb.append(RECEIPT_SEPARATOR).append("\n");

        AtomicInteger sandwichNumber = new AtomicInteger(1);
        sandwiches.stream()
                .forEach(sandwich -> {
                    sb.append("Sandwich ").append(sandwichNumber.getAndIncrement()).append(":\n");
                    sb.append(sandwich.toReceiptText()).append("\n");
                });

        if (!drinks.isEmpty()) {
            sb.append("Drinks:\n");
            drinks.stream()
                    .forEach(drink -> sb.append("  - ").append(drink.toReceiptText()).append("\n"));
            sb.append("\n");
        }

        if (!chips.isEmpty()) {
            sb.append("Chips:\n");
            chips.stream()
                    .forEach(chip -> sb.append("  - ").append(chip.toReceiptText()).append("\n"));
            sb.append("\n");
        }

        BigDecimal subtotal = calculateSubtotal();
        sb.append(RECEIPT_SEPARATOR).append("\n");
        sb.append("Total: $").append(subtotal.toPlainString()).append("\n");
        sb.append("Payment Method: ").append(paymentMethod.getDisplayName()).append("\n");
        sb.append(RECEIPT_SEPARATOR).append("\n");
        sb.append("Thank you for your order!\n");
        return sb.toString();
    }

    // Saves receipt to file and returns the file path
    public String saveReceipt() throws IOException {
        String content = buildReceiptText();
        return ReceiptService.saveReceipt(placedAt, content);
    }
}

