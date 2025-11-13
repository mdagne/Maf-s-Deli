package com.pluralsight;

import com.pluralsight.services.OrderManager;
import com.pluralsight.ui.HomeScreen;

public class Main {
    public static void main(String[] args) {
       
        // Create OrderManager to manage order state
        OrderManager orderManager = new OrderManager();
        
        // Create and display the home screen
        HomeScreen homeScreen = new HomeScreen(orderManager);
        homeScreen.display();
        
    }
}

