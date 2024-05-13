package it.polimi.ingsw;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;

public class Codex {

    private Lobby lobby;
    private ClientController clientController;

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public ClientController getClientController() {
        return clientController;
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
            if(args[0].equals("server") && args.length == 2) {
                codex.setLobby(new Lobby(Integer.parseInt(args[1])));
                codex.getLobby().startLobby();
            }
            else {
                System.out.println("unexpected arguments");
            }
        }

    }

    public void shutdown() {
        if(lobby != null) { lobby.shutdown(); }
        if(clientController != null) { clientController.shutdown(); }

    }
}
