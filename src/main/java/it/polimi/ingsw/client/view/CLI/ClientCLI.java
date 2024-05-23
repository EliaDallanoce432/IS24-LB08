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

public class ClientCLI {
    private static final ClientCLI instance = new ClientCLI();
    private final ClientTerminalInputReader clientTerminalInputReader;
    private final Thread clientTerminalInputThread;

    public ClientCLI() {
        clientTerminalInputReader = new ClientTerminalInputReader();
        clientTerminalInputThread = new Thread(clientTerminalInputReader);
    }

    public static ClientCLI getInstance() {
        return instance;
    }
    public void start() {
        String address = getServerAddress("localhost");
        int port = getServerPort(12345);
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

    private void printMenu()
    {
        Map<String, String> menuOptions = new HashMap<>();
        menuOptions.put("setusername | su <username>", "Set your username");
        menuOptions.put("join | j <gameName>", "Join to a game");
        menuOptions.put("create | c <gameName> <players(2-4)>", "Create a game for 2 to 4 players");
        menuOptions.put("availablegames | ag", "View all available games");
        menuOptions.put("quit | q", "Exit from Codex");
        Menu menu = new Menu(menuOptions);
        menu.displayMenu();
    }

    private String getServerAddress(String defaultValue) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert server IP address (press Enter for set default IP: " + defaultValue +")");
        String address = scanner.nextLine().trim();
        return address.isEmpty() ? defaultValue : address;
    }

    private int getServerPort(int defaultValue) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert server port (press Enter for set default port: " + defaultValue +")");
        String portString = scanner.nextLine().trim();
        try {
            return portString.isEmpty() ? defaultValue : Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Using default port: " + defaultValue);
            return defaultValue;
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
    private static void clearConsole() throws IOException, InterruptedException{
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            Runtime.getRuntime().exec("clear");
        }
    }

    public void shutdown() {
        clientTerminalInputReader.shutdown();
        ClientController.getInstance().shutdownForGUI();
    }
}
