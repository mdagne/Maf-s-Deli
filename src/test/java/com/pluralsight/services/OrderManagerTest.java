package com.pluralsight.services;

import com.pluralsight.models.Order;
import com.pluralsight.models.Sandwich;
import com.pluralsight.sides.Drink;
import com.pluralsight.sides.Chips;
import com.pluralsight.enums.SandwichSize;
import com.pluralsight.enums.BreadType;
import com.pluralsight.enums.DrinkSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for OrderManager.
 * Tests inheritance, polymorphism, and object composition.
 */
public class OrderManagerTest {
    
    private OrderManager orderManager;
    
    @BeforeEach
    public void setUp() {
        orderManager = new OrderManager();
    }
    
    @Test
    public void testOrderManagerInitialization() {
        assertNotNull(orderManager);
        assertNotNull(orderManager.getCurrentOrder());
    }
    
    @Test
    public void testStartNewOrder() {
        orderManager.startNewOrder("Alice");
        Order order = orderManager.getCurrentOrder();
        
        assertNotNull(order);
        assertEquals("Alice", order.getCustomerName());
        assertTrue(order.getSandwiches().isEmpty());
    }
    
    @Test
    public void testAddSandwichToOrder() {
        Sandwich sandwich = new Sandwich(SandwichSize.EIGHT, BreadType.WHITE);
        orderManager.addSandwich(sandwich);
        
        assertEquals(1, orderManager.getCurrentOrder().getSandwiches().size());
    }
    
    @Test
    public void testAddDrinkToOrder() {
        Drink drink = new Drink("Pepsi", DrinkSize.LARGE);
        orderManager.addDrink(drink);
        
        assertEquals(1, orderManager.getCurrentOrder().getDrinks().size());
    }
    
    @Test
    public void testAddChipsToOrder() {
        Chips chips = new Chips("Plain");
        orderManager.addChips(chips);
        
        assertEquals(1, orderManager.getCurrentOrder().getChips().size());
    }
    
    @Test
    public void testCancelOrder() {
        Sandwich sandwich = new Sandwich(SandwichSize.FOUR, BreadType.WHEAT);
        orderManager.addSandwich(sandwich);
        orderManager.cancelOrder();
        
        assertTrue(orderManager.getCurrentOrder().getSandwiches().isEmpty());
    }
    
    @Test
    public void testCanCheckout() {
        Sandwich sandwich = new Sandwich(SandwichSize.EIGHT, BreadType.RYE);
        orderManager.addSandwich(sandwich);
        
        assertTrue(orderManager.canCheckout());
    }
}
