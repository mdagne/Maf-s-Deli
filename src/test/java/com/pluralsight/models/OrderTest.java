package com.pluralsight.models;

import com.pluralsight.sides.Drink;
import com.pluralsight.sides.Chips;
import com.pluralsight.enums.DrinkSize;
import com.pluralsight.enums.PaymentMethod;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Order.
 * Tests aggregation, polymorphism with MenuItem interface, and stream usage.
 */
public class OrderTest {
    
    private Order order;
    
    @BeforeEach
    public void setUp() {
        order = new Order("John Doe");
    }
    
    @Test
    public void testOrderConstruction() {
        assertNotNull(order);
        assertNotNull(order.getId());
        assertNotNull(order.getPlacedAt());
    }
    
    @Test
    public void testAddSandwich() {
        Sandwich sandwich = new Sandwich(com.pluralsight.enums.SandwichSize.EIGHT, 
                com.pluralsight.enums.BreadType.WHITE);
        order.addSandwich(sandwich);
        
        assertEquals(1, order.getSandwiches().size());
    }
    
    @Test
    public void testAddDrink() {
        Drink drink = new Drink("Coke", DrinkSize.LARGE);
        order.addDrink(drink);
        
        assertEquals(1, order.getDrinks().size());
    }
    
    @Test
    public void testAddChips() {
        Chips chips = new Chips("BBQ");
        order.addChips(chips);
        
        assertEquals(1, order.getChips().size());
    }
    
    @Test
    public void testCalculateSubtotal() {
        Sandwich sandwich = new Sandwich(com.pluralsight.enums.SandwichSize.EIGHT, 
                com.pluralsight.enums.BreadType.WHITE);
        Drink drink = new Drink("Sprite", DrinkSize.MEDIUM);
        
        order.addSandwich(sandwich);
        order.addDrink(drink);
        
        BigDecimal subtotal = order.calculateSubtotal();
        assertTrue(subtotal.compareTo(BigDecimal.ZERO) > 0);
    }
    
    @Test
    public void testOrderValidation() {
        Sandwich sandwich = new Sandwich(com.pluralsight.enums.SandwichSize.EIGHT, 
                com.pluralsight.enums.BreadType.WHITE);
        order.addSandwich(sandwich);
        
        assertTrue(order.isValid());
    }
    
    @Test
    public void testBuildReceiptText() {
        Sandwich sandwich = new Sandwich(com.pluralsight.enums.SandwichSize.EIGHT, 
                com.pluralsight.enums.BreadType.WHITE);
        order.addSandwich(sandwich);
        
        String receipt = order.buildReceiptText();
        assertNotNull(receipt);
        assertTrue(receipt.contains("Maf's Deli Receipt"));
    }
    
    @Test
    public void testPaymentMethodDefault() {
        assertEquals(PaymentMethod.CASH, order.getPaymentMethod());
    }
    
    @Test
    public void testSetPaymentMethod() {
        order.setPaymentMethod(PaymentMethod.CARD);
        assertEquals(PaymentMethod.CARD, order.getPaymentMethod());
    }
}
