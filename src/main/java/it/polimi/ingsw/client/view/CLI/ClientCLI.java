package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;

import java.util.Scanner;

public class ClientCLI {
    private static ClientCLI instance;
    private ClientTerminalInputReader clientTerminalInputReader;
    private Thread clientTerminalInputThread;

    public void start() {
        getIPAndPort();
        clientTerminalInputReader = new ClientTerminalInputReader();
        clientTerminalInputThread = new Thread(clientTerminalInputReader);
        clientTerminalInputThread.start();
    }

    public static ClientCLI getInstance() {
        if(instance == null) {
            instance = new ClientCLI();
            return instance;
        }
        else return instance;
    }

    private static void getIPAndPort() {
        Scanner scanner = new Scanner(System.in);
        boolean connected = false;

        while (!connected) {
            System.out.println("Insert server IP address (default: 'localhost'):");
            String address = scanner.nextLine();
            if (address.equals("")) address = "localhost";
            System.out.println("Insert server port (default: '12345'):");
            String port = scanner.nextLine();
            if (port.equals("")) port = "12345";
            try {
                ClientController.getInstance(address, Integer.parseInt(port));
                connected = true;
                System.out.println("Connected successfully to the Lobby!");
            } catch (ServerUnreachableException e) {
                System.out.println("could not connect to server, try again");
            }
        }
    }


    public void shutdown() {
        ClientController.getInstance().shutdown();
    }
}
