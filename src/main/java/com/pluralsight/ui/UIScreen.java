package com.pluralsight.ui;

// UIScreen defines the template method pattern for rendering header, content, and footer.
public abstract class UIScreen {
    
    // Template method that defines the algorithm structure
    public final void display() {
        renderHeader();
        renderContent();
        renderFooter();
    }
    
    // Renders the header of the screen
    protected void renderHeader() {
        // Default implementation - can be overridden
    }
    
    // Renders the main content of the screen - must be implemented by subclasses
    protected abstract void renderContent();
    
    // Renders the footer of the screen
    protected void renderFooter() {
        // Default implementation - can be overridden
    }
}
