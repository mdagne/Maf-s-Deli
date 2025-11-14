package com.pluralsight.SignatureSandwichTest;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.enums.BreadType;
import com.pluralsight.models.SignatureSandwich;
import com.pluralsight.toppings.Meat;
import com.pluralsight.toppings.Cheese;
import com.pluralsight.toppings.Vegetable;
import com.pluralsight.toppings.Sauce;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for SignatureSandwich.
 * Tests inheritance, factory methods, default toppings, and receipt formatting.
 */
public class SignatureSandwichTest {

    @Test
    public void testSignatureSandwichConstructor() {
        SignatureSandwich sandwich = new SignatureSandwich("Custom Signature",
                SandwichSize.TWELVE, BreadType.WHEAT);

        assertNotNull(sandwich);
        assertEquals("Custom Signature", sandwich.getSignatureName());
        assertEquals(SandwichSize.TWELVE, sandwich.getSize());
        assertEquals(BreadType.WHEAT, sandwich.getBread());
    }

    @Test
    public void testCreateBLT() {
        SignatureSandwich blt = SignatureSandwich.createBLT();

        assertNotNull(blt);
        assertEquals("BLT", blt.getSignatureName());
        assertEquals(SandwichSize.EIGHT, blt.getSize());
        assertEquals(BreadType.WHITE, blt.getBread());
        assertTrue(blt.isToasted());

        // Verify default toppings are added
        assertEquals(2, blt.getPremiumToppings().size());
        assertTrue(blt.getRegularToppings().size() >= 3);

        // Verify BLT has Bacon and Cheddar
        boolean hasBacon = blt.getPremiumToppings().keySet().stream()
                .anyMatch(t -> t instanceof Meat && t.getName().equals("Bacon"));
        boolean hasCheddar = blt.getPremiumToppings().keySet().stream()
                .anyMatch(t -> t instanceof Cheese && t.getName().equals("Cheddar"));

        assertTrue(hasBacon, "BLT should have Bacon");
        assertTrue(hasCheddar, "BLT should have Cheddar");
    }

    @Test
    public void testCreatePhillyCheeseSteak() {
        SignatureSandwich philly = SignatureSandwich.createPhillyCheeseSteak();

        assertNotNull(philly);
        assertEquals("Philly Cheese Steak", philly.getSignatureName());
        assertEquals(SandwichSize.EIGHT, philly.getSize());
        assertEquals(BreadType.WHITE, philly.getBread());
        assertTrue(philly.isToasted());

        // Verify default toppings are added
        assertEquals(2, philly.getPremiumToppings().size());
        assertTrue(philly.getRegularToppings().size() >= 2);

        // Verify Philly has Steak and American cheese
        boolean hasSteak = philly.getPremiumToppings().keySet().stream()
                .anyMatch(t -> t instanceof Meat && t.getName().equals("Steak"));
        boolean hasAmerican = philly.getPremiumToppings().keySet().stream()
                .anyMatch(t -> t instanceof Cheese && t.getName().equals("American"));

        assertTrue(hasSteak, "Philly should have Steak");
        assertTrue(hasAmerican, "Philly should have American cheese");
    }


    @Test
    public void testReceiptTextIncludesSignatureName() {
        SignatureSandwich blt = SignatureSandwich.createBLT();
        String receipt = blt.toReceiptText();

        assertNotNull(receipt);
        assertTrue(receipt.contains("Signature: BLT"),
                "Receipt should include signature name");
        assertTrue(receipt.contains("BLT"),
                "Receipt should contain the signature name");
    }

    @Test
    public void testPolymorphismWithPricable() {
        SignatureSandwich signature = SignatureSandwich.createBLT();

        // Test that SignatureSandwich implements Pricable interface
        com.pluralsight.interfaces.Pricable pricable = signature;
        assertNotNull(pricable);

        BigDecimal price = pricable.getPrice();
        assertNotNull(price);
        assertTrue(price.compareTo(BigDecimal.ZERO) > 0,
                "Signature sandwich should have a positive price");

        String receiptText = pricable.toReceiptText();
        assertNotNull(receiptText);
        assertTrue(receiptText.contains("Signature:"));
    }

    @Test
    public void testPriceCalculation() {
        SignatureSandwich blt = SignatureSandwich.createBLT();
        BigDecimal price = blt.getPrice();

        assertNotNull(price);
        assertTrue(price.compareTo(BigDecimal.ZERO) > 0);

        // BLT should cost more than base price due to premium toppings
        BigDecimal basePrice = com.pluralsight.services.PricingService.getBreadPrice(SandwichSize.EIGHT);
        assertTrue(price.compareTo(basePrice) > 0,
                "BLT price should be greater than base bread price");
    }

    @Test
    public void testCanAddAdditionalToppings() {
        SignatureSandwich blt = SignatureSandwich.createBLT();
        int initialRegularCount = blt.getRegularToppings().size();

        // Add an additional topping
        blt.addRegularTopping(new Vegetable("Onion"));

        assertEquals(initialRegularCount + 1, blt.getRegularToppings().size(),
                "Should be able to add additional toppings to signature sandwich");
    }
}
