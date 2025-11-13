package com.pluralsight.models;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.enums.BreadType;
import com.pluralsight.toppings.Meat;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Sandwich.
 * Tests inheritance, constructors, polymorphism, and method overriding.
 */
public class SandwichTest {
    
    private Sandwich sandwich;
    
    @BeforeEach
    public void setUp() {
        // Test constructor and inheritance
        sandwich = new Sandwich(SandwichSize.EIGHT, BreadType.WHITE);
    }
    
    @Test
    public void testSandwichConstructor() {
        assertNotNull(sandwich);
        assertEquals(SandwichSize.EIGHT, sandwich.getSize());
        assertEquals(BreadType.WHITE, sandwich.getBread());
        assertFalse(sandwich.isToasted());
    }
    
    @Test
    public void testAddPremiumTopping() {
        Meat meat = new Meat("Steak");
        sandwich.addPremiumTopping(meat, 1, 0);
        
        assertEquals(1, sandwich.getPremiumToppings().size());
    }
    
    @Test
    public void testPriceCalculation() {
        BigDecimal price = sandwich.getPrice();
        assertNotNull(price);
        assertTrue(price.compareTo(BigDecimal.ZERO) >= 0);
    }
    
    @Test
    public void testSetToasted() {
        sandwich.setToasted(true);
        assertTrue(sandwich.isToasted());
    }
    
    @Test
    public void testToReceiptText() {
        Meat meat = new Meat("Bacon");
        sandwich.addPremiumTopping(meat, 1, 0);
        sandwich.setToasted(true);
        
        String receipt = sandwich.toReceiptText();
        assertNotNull(receipt);
        assertTrue(receipt.contains("Toasted"));
        assertTrue(receipt.contains("Bacon"));
    }
    
    @Test
    public void testPolymorphism() {
        // Test that Sandwich implements Pricable interface
        com.pluralsight.interfaces.Pricable pricable = sandwich;
        assertNotNull(pricable.getPrice());
        assertNotNull(pricable.toReceiptText());
    }
}
