package com.pluralsight.models;

import com.pluralsight.enums.SandwichSize;
import com.pluralsight.enums.BreadType;
import com.pluralsight.toppings.Meat;
import com.pluralsight.toppings.Cheese;
import com.pluralsight.toppings.Vegetable;
import com.pluralsight.toppings.Sauce;

// SignatureSandwich represents a predefined sandwich template customers can customize (e.g., BLT, Philly Cheese Steak).
public class SignatureSandwich extends Sandwich {
    
    private final String signatureName;

    public SignatureSandwich(String signatureName, SandwichSize size, BreadType bread) {
        super(size, bread);
        this.signatureName = signatureName;
    }

    public String getSignatureName() {
        return signatureName;
    }

    // Creates a BLT signature sandwich with default toppings (8" white bread, Bacon, Cheddar, Lettuce, Tomato, Ranch, Toasted).
    public static SignatureSandwich createBLT() {
        SignatureSandwich blt = new SignatureSandwich("BLT", SandwichSize.EIGHT, BreadType.WHITE);
        blt.addPremiumTopping(new Meat("Bacon"), 1, 0);
        blt.addPremiumTopping(new Cheese("Cheddar"), 1, 0);
        blt.addRegularTopping(new Vegetable("Lettuce"));
        blt.addRegularTopping(new Vegetable("Tomato"));
        blt.addRegularTopping(new Sauce("Ranch"));
        blt.setToasted(true);
        return blt;
    }

    // Creates a Philly Cheese Steak signature sandwich with default toppings (8" white bread, Steak, American Cheese, Peppers, Mayo, Toasted).
    public static SignatureSandwich createPhillyCheeseSteak() {
        SignatureSandwich philly = new SignatureSandwich("Philly Cheese Steak", SandwichSize.EIGHT, BreadType.WHITE);
        philly.addPremiumTopping(new Meat("Steak"), 1, 0);
        philly.addPremiumTopping(new Cheese("American"), 1, 0);
        philly.addRegularTopping(new Vegetable("Peppers"));
        philly.addRegularTopping(new Sauce("Mayo"));
        philly.setToasted(true);
        return philly;
    }

    @Override
    public String toReceiptText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Signature: %s\n", signatureName));
        sb.append(super.toReceiptText());
        return sb.toString();
    }
}

