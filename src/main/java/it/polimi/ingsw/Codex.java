package it.polimi.ingsw;

import it.polimi.ingsw.client.view.CLI.ClientCLI;
import it.polimi.ingsw.client.view.GUI.ClientGUI;
import it.polimi.ingsw.server.lobby.Lobby;
import javafx.application.Application;

/**
 * The main class with the entry point for the Codex Naturalis application.
 */
public class Codex {

    private Lobby lobby;

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
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
            codex.setLobby(new Lobby());
            codex.getLobby().startLobby();
        } else {
            printUsageMessage();
            codex.shutdown();
        }
    }

    private static void printUsageMessage() {
        System.out.println("Wrong arguments. Please specify one of these options:");
        System.out.println("┌───────────────────┬───────────────────────────┐");
        System.out.println("│      Options      │        Description        │");
        System.out.println("├───────────────────┴───────────────────────────┤");
        System.out.printf("%-15s %-20s", "│ server            │", "Open Server configuration │\n");
        System.out.println("├───────────────────┴───────────────────────────┤");
        System.out.printf("%-15s %-15s", "│ client            │", "Run GUI                   │\n");
        System.out.println("├───────────────────┴───────────────────────────┤");
        System.out.printf("%-15s %-15s", "│ client --cli      │", "Run CLI                   │\n");
        System.out.println("└───────────────────┴───────────────────────────┘");
        System.out.println();
    }

    private void shutdown() {
        if(lobby != null) { lobby.shutdown(); }
    }


}
