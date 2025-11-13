package com.pluralsight.services;

import com.pluralsight.enums.SandwichSize;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for PricingService.
 * Tests static methods and price calculations.
 */
public class PricingServiceTest {
    
    @Test
    public void testBreadPrice() {
        BigDecimal price4 = PricingService.getBreadPrice(SandwichSize.FOUR);
        BigDecimal price8 = PricingService.getBreadPrice(SandwichSize.EIGHT);
        BigDecimal price12 = PricingService.getBreadPrice(SandwichSize.TWELVE);
        
        assertEquals(new BigDecimal("5.50"), price4);
        assertEquals(new BigDecimal("7.00"), price8);
        assertEquals(new BigDecimal("8.50"), price12);
    }
    
    @Test
    public void testMeatPrice() {
        BigDecimal price4 = PricingService.getMeatPrice(SandwichSize.FOUR);
        BigDecimal price8 = PricingService.getMeatPrice(SandwichSize.EIGHT);
        BigDecimal price12 = PricingService.getMeatPrice(SandwichSize.TWELVE);
        
        assertEquals(new BigDecimal("1.00"), price4);
        assertEquals(new BigDecimal("2.00"), price8);
        assertEquals(new BigDecimal("3.00"), price12);
    }
    
    @Test
    public void testExtraMeatPrice() {
        BigDecimal price4 = PricingService.getExtraMeatPrice(SandwichSize.FOUR);
        BigDecimal price8 = PricingService.getExtraMeatPrice(SandwichSize.EIGHT);
        BigDecimal price12 = PricingService.getExtraMeatPrice(SandwichSize.TWELVE);
        
        assertEquals(new BigDecimal("0.50"), price4);
        assertEquals(new BigDecimal("1.00"), price8);
        assertEquals(new BigDecimal("1.50"), price12);
    }
    
    @Test
    public void testCheesePrice() {
        BigDecimal price4 = PricingService.getCheesePrice(SandwichSize.FOUR);
        BigDecimal price8 = PricingService.getCheesePrice(SandwichSize.EIGHT);
        BigDecimal price12 = PricingService.getCheesePrice(SandwichSize.TWELVE);
        
        assertEquals(new BigDecimal("0.75"), price4);
        assertEquals(new BigDecimal("1.50"), price8);
        assertEquals(new BigDecimal("2.25"), price12);
    }
    
    @Test
    public void testExtraCheesePrice() {
        BigDecimal price4 = PricingService.getExtraCheesePrice(SandwichSize.FOUR);
        BigDecimal price8 = PricingService.getExtraCheesePrice(SandwichSize.EIGHT);
        BigDecimal price12 = PricingService.getExtraCheesePrice(SandwichSize.TWELVE);
        
        assertEquals(new BigDecimal("0.30"), price4);
        assertEquals(new BigDecimal("0.60"), price8);
        assertEquals(new BigDecimal("0.90"), price12);
    }
}
