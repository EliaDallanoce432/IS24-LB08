package it.polimi.ingsw.client.view.CLI;
import java.util.Map;
import java.util.Scanner;

import static it.polimi.ingsw.util.supportclasses.Constants.MENU_HEADER;

public class Menu {
    private final Map<String, String> options;
    public Menu(Map<String, String> options) {
        this.options = options;
    }
    public void displayMenu() {
        System.out.println(MENU_HEADER);
        for (Map.Entry<String, String> entry : options.entrySet()) {
            System.out.printf("%-50s %-20s", entry.getKey(), entry.getValue());
            System.out.println();
        }
        System.out.println("Enter your choice: ");
    }

}
