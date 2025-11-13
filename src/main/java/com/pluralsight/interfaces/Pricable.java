package com.pluralsight.interfaces;

import java.math.BigDecimal;

public interface Pricable {
    BigDecimal getPrice();
    String toReceiptText();
}

