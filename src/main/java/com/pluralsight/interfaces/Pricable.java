package com.pluralsight.interfaces;

import java.math.BigDecimal;

// Interface that defines contract for all orderable items
public interface Pricable {
    BigDecimal getPrice();
    String toReceiptText();
}

