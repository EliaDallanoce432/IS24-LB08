package it.polimi.ingsw;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.CLI.ClientCLI;
import it.polimi.ingsw.client.view.ClientGUI;
import it.polimi.ingsw.server.lobby.Lobby;
import javafx.application.Application;

public class Codex {

    private Lobby lobby;
    private ClientController clientController;

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public static void main(String[] args) {
        Codex codex = new Codex();
        if (args.length == 0) {
            printUsageMessage();
            codex.shutdown();
        } else if (args[0].equals("client")) {
            if (args.length == 1) {
                Application.launch(ClientGUI.class);
            } else if (args[1].equals("--cli")) {
                ClientCLI.getInstance().start();
            } else
                System.out.println("unexpected argument: " + args[1]);
        } else if (args[0].equals("server")) {
//            int port = handleServerArguments(args);
            codex.setLobby(new Lobby());
            codex.getLobby().startLobby();
        } else {
            printUsageMessage();
            codex.shutdown();
        }
    }

    private static void printUsageMessage() {
        System.out.println("Wrong arguments. Please specify one of these options:");
        System.out.println("server (run Server)");
//        System.out.println("server <port> (run Server at specific port)");
        System.out.println("client (open default GUI)");
        System.out.println("client --cli (open CLI)");
    }

//    private static int handleServerArguments(String[] args) {
//        if (args.length == 1) {
//            return 12345; // Use default port
//        } else if (args.length == 2) {
//            try {
//                return Integer.parseInt(args[1]);
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid port number: " + args[1]);
//                System.out.println("Please specify a valid port number (integer)");
//            }
//        } else {
//            System.out.println("Unexpected server arguments");
//        }
//        return -1;
//    }

    private void shutdown() {
        if(lobby != null) { lobby.shutdown(); }
        if(clientController != null) { clientController.shutdown(); }
    }


}
