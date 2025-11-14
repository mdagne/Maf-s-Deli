package com.pluralsight.utils;

import com.pluralsight.enums.BreadType;
import com.pluralsight.enums.DrinkSize;
import com.pluralsight.enums.SandwichSize;
import com.pluralsight.toppings.Meat;
import com.pluralsight.toppings.Cheese;
import com.pluralsight.toppings.Vegetable;
import com.pluralsight.toppings.Sauce;
import com.pluralsight.sides.Drink;
import com.pluralsight.sides.SauceSide;

import java.util.*;

// MenuUtils provides static methods for reading user input and displaying menu choices for all order items.
public class MenuUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Invalid input. Enter 'y' or 'n'.");
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static SandwichSize chooseSandwichSize() {
        System.out.println("\nSelect sandwich size:");
        System.out.println("1) 4\"");
        System.out.println("2) 8\"");
        System.out.println("3) 12\"");
        while (true) {
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: return SandwichSize.FOUR;
                case 2: return SandwichSize.EIGHT;
                case 3: return SandwichSize.TWELVE;
                default: System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public static BreadType chooseBreadType() {
        System.out.println("\nSelect bread type:");
        System.out.println("1) White");
        System.out.println("2) Wheat");
        System.out.println("3) Rye");
        System.out.println("4) Wrap");
        while (true) {
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: return BreadType.WHITE;
                case 2: return BreadType.WHEAT;
                case 3: return BreadType.RYE;
                case 4: return BreadType.WRAP;
                default: System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    public static List<String> chooseMeats() {
        return chooseMultipleOptions("meat", Meat.getAvailableMeats());
    }

    public static List<String> chooseCheeses() {
        return chooseMultipleOptions("cheese", Cheese.getAvailableCheeses());
    }

    public static List<String> chooseRegularToppings() {
        return chooseMultipleOptions("regular topping", Vegetable.getAvailableVegetables());
    }

    public static List<String> chooseSauces() {
        return chooseMultipleOptions("sauce", Sauce.getAvailableSauces());
    }

    public static List<String> chooseSides() {
        return chooseMultipleOptions("side", SauceSide.getAvailableSauceSides());
    }

    private static List<String> chooseMultipleOptions(String itemType, List<String> options) {
        List<String> selected = new ArrayList<>();
        System.out.println("\nAvailable " + itemType + "s:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ") " + options.get(i));
        }
        System.out.println("0) No " + itemType);
        
        Set<Integer> selectedIndices = new HashSet<>();
        while (true) {
            int choice = readInt("Enter your choice (0 for no " + itemType + "): ");
            if (choice == 0) {
                break;
            }
            if (choice < 1 || choice > options.size()) {
                System.out.println("Invalid choice. Please enter a number between 1 and " + options.size() + ", or 0 for no " + itemType + ".");
                continue;
            }
            int index = choice - 1;
            String option = options.get(index);
            if (selectedIndices.contains(index)) {
                if (readYesNo(option + " is already selected. Remove it? (y/n): ")) {
                    selectedIndices.remove(index);
                    selected.remove(option);
                    System.out.println(option + " removed.");
                }
            } else {
                selectedIndices.add(index);
                selected.add(option);
                System.out.println(option + " added.");
            }
        }
        return selected;
    }

    public static int readExtraCount(String itemName, String itemType) {
        while (true) {
            try {
                System.out.print("How many extra " + itemName + " " + itemType + "s? (0 for none): ");
                int count = Integer.parseInt(scanner.nextLine().trim());
                if (count < 0) {
                    System.out.println("Please enter a number >= 0.");
                    continue;
                }
                return count;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static DrinkSize chooseDrinkSize() {
        System.out.println("\nSelect drink size:");
        System.out.println("1) Small");
        System.out.println("2) Medium");
        System.out.println("3) Large");
        while (true) {
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: return DrinkSize.SMALL;
                case 2: return DrinkSize.MEDIUM;
                case 3: return DrinkSize.LARGE;
                default: System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public static String chooseDrink() {
        List<String> drinks = Drink.getAvailableDrinks();
        System.out.println("\nAvailable drinks:");
        for (int i = 0; i < drinks.size(); i++) {
            System.out.println((i + 1) + ") " + drinks.get(i));
        }
        System.out.println("0) Skip/No drink");
        while (true) {
            int choice = readInt("Enter your choice (0 to skip): ");
            if (choice == 0) {
                return null;
            }
            if (choice >= 1 && choice <= drinks.size()) {
                return drinks.get(choice - 1);
            }
            System.out.println("Invalid choice. Please enter a number between 1 and " + drinks.size() + ", or 0 to skip.");
        }
    }


    public static com.pluralsight.enums.PaymentMethod choosePaymentMethod() {
        System.out.println("\nSelect payment method:");
        System.out.println("1) Cash");
        System.out.println("2) Card");
        while (true) {
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: return com.pluralsight.enums.PaymentMethod.CASH;
                case 2: return com.pluralsight.enums.PaymentMethod.CARD;
                default: System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }
}
