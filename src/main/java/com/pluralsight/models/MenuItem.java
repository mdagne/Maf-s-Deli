package com.pluralsight.models;

import com.pluralsight.interfaces.Pricable;

// MenuItem represents any item that can be added to an order; uses polymorphism for uniform treatment.
public interface MenuItem extends Pricable {
    // Gets a description of the menu item
    String getDescription();
    
    // Gets the category of the menu item (Sandwich, Drink, Chips, etc.)
    String getCategory();

    // remove
}

