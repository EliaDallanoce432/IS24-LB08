package it.polimi.ingsw.client.view.CLI;

import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static final String MENU_HEADER = "\n*** Main Menu ***\n";
    private static final String MENU_PROMPT = "Enter your choice: ";
    private final Map<String, String> options;
    public Menu(Map<String, String> options) {
        this.options = options;
    }

    public void displayMenu() {
        System.out.println(MENU_HEADER);
        for (Map.Entry<String, String> entry : options.entrySet()) {
            System.out.println(entry.getKey() +"\t\t" + entry.getValue());
        }
        System.out.println(MENU_PROMPT);
    }

    public String getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim(); // Convert to uppercase for case-insensitive matching
        if (!options.containsKey(choice)) {
            System.out.println("Invalid choice. Please try again.");
            return getUserChoice();
        }
        return choice;
    }

}
