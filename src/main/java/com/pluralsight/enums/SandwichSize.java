package com.pluralsight.enums;

// sandwich sizes options
public enum SandwichSize {
    FOUR, EIGHT, TWELVE;

    public String toDisplay() {
        switch (this) {
            case FOUR: return "4\"";
            case EIGHT: return "8\"";
            case TWELVE: return "12\"";
            default: return this.name();
        }
    }
}

