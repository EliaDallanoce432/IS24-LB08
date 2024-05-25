package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;

import java.io.IOException;
import java.util.Scanner;

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
        StageManager.enableCLIMode();
        try {
            ClientController.getInstance(address, port);
        } catch (ServerUnreachableException e) {
            System.out.println("Could not connect to server.");
            System.exit(1);
        }
        clientTerminalInputThread.start();
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

    /**
     * Clears the console screen depending on the operating system (Windows or Unix-based).
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Shuts down the CLI by:
     */
    public void shutdown() {
        clientTerminalInputReader.shutdown();
        ClientController.getInstance().shutdownForGUI();
    }
}
