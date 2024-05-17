package it.polimi.ingsw;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.util.customexceptions.CannotOpenWelcomeSocket;

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
        if(args.length == 0) {
            codex.setClientController(ClientController.getInstance("localhost", 12345));
        }
        else {
            int port = 12345;
            if(args[0].equals("server")) {
                if(args.length == 2) {
                    port = Integer.parseInt(args[1]);
                }
                try {
                    codex.setLobby(new Lobby(port));
                    codex.getLobby().startLobby();
                } catch (CannotOpenWelcomeSocket e) {
                    System.out.println("The server could not start the welcome socket at port " + port);
                    codex.shutdown();
                }
            }
            else {
                System.out.println("unexpected arguments");
                codex.shutdown();
            }
        }
    }

    public void shutdown() {
        if(lobby != null) { lobby.shutdown(); }
        if(clientController != null) { clientController.shutdown(); }

    }
}
