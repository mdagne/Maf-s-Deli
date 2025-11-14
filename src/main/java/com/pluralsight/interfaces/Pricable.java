package com.pluralsight.interfaces;

import java.math.BigDecimal;

// Defines methods for getting price and generating receipt text for all orderable items.
public interface Pricable {
    BigDecimal getPrice();
    String toReceiptText();
}

