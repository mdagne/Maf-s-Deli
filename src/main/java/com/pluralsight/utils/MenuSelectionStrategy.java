package com.pluralsight.utils;

import java.util.List;

// Strategy interface for menu item selection using the strategy pattern.
public interface MenuSelectionStrategy {
    // Get available options
    List<String> getAvailableOptions();
    
    // Get item type name
    String getItemTypeName();
}
