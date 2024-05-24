package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import it.polimi.ingsw.util.supportclasses.ConsoleColor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static it.polimi.ingsw.util.supportclasses.Constants.MENU_HEADER;

/**
 * This class provides the command-line interface (CLI) for the Codex game client.
 * It handles user interactions, displays menus, and retrieves user input.
 */
public class ClientCLI {
    private static final ClientCLI instance = new ClientCLI();
    private final ClientTerminalInputReader clientTerminalInputReader;
    private final Thread clientTerminalInputThread;

    public ClientCLI() {
        clientTerminalInputReader = new ClientTerminalInputReader();
        clientTerminalInputThread = new Thread(clientTerminalInputReader);
    }

    /**
     * Returns the existing instance of ClientCLI (Singleton pattern).
     * @return The ClientCLI instance.
     */
    public static ClientCLI getInstance() {
        return instance;
    }
    /**
     * Starts the CLI by prompting for server address and port
     */
    public void start() {
        String address = getServerAddress();
        int port = getServerPort();
        try {
            ClientController.getInstance(address, port);
            printWelcomeMessage();
            printMenu();
        } catch (ServerUnreachableException e) {
            System.out.println("Could not connect to server.");
            System.exit(1);
        }
        StageManager.enableCLIMode();
        clientTerminalInputThread.start();
    }

    /**
     * Displays the main menu options along with their descriptions.
     */
    private void printMenu()
    {
        Map<String, String> menuOptions = new HashMap<>();
        menuOptions.put("setusername | su <username>", "Set your username");
        menuOptions.put("join | j <gameName>", "Join to a game");
        menuOptions.put("create | c <gameName> <players(2-4)>", "Create a game for 2 to 4 players");
        menuOptions.put("availablegames | ag", "View all available games");
        menuOptions.put("quit | q", "Exit from Codex");
        System.out.println(MENU_HEADER);
        for (Map.Entry<String, String> entry : menuOptions.entrySet()) {
            System.out.printf("%-50s %-20s", entry.getKey(), entry.getValue());
            System.out.println();
        }
        System.out.println("Enter your choice: ");
    }

    /**
     * Prompts the user for the server's IP address. Allows using Enter for the default value.
     * @return The server's IP address entered by the user.
     */
    private String getServerAddress() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert server IP address (press Enter for set default IP: localhost");
        String address = scanner.nextLine().trim();
        return address.isEmpty() ? "localhost" : address;
    }

    /**
     * Prompts the user for the server's port number. Allows using Enter for the default value.
     * Handles invalid input and uses the default port if necessary.
     * @return The server's port number entered by the user.
     */
    private int getServerPort() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert server port (press Enter for set default port: 12345");
        String portString = scanner.nextLine().trim();
        try {
            return portString.isEmpty() ? 12345 : Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Using default port: " + 12345);
            return 12345;
        }
    }

    private void printWelcomeMessage() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Codex_logo.txt");
        if (is != null) {
            try (Scanner sc = new Scanner(is)) {
                clearConsole();
                while (sc.hasNextLine()) {
                    System.out.println(ConsoleColor.GREEN + sc.nextLine() + ConsoleColor.RESET);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Clears the console screen depending on the operating system (Windows or Unix-based).
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the thread is interrupted.
     */
    private static void clearConsole() throws IOException, InterruptedException{
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            Runtime.getRuntime().exec("clear");
        }
    }
    /**
     * Shuts down the CLI by:
     */
    public void shutdown() {
        clientTerminalInputReader.shutdown();
        ClientController.getInstance().shutdownForGUI();
    }
}
