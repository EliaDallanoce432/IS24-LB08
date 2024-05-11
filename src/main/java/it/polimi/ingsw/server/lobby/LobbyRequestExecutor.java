package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;
import it.polimi.ingsw.util.customexceptions.NonExistentGameException;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

public class LobbyRequestExecutor {
    private final Lobby lobby;

    public LobbyRequestExecutor(Lobby lobby) {
        this.lobby = lobby;
    }
    /**
     * method handles the incoming request from a client
     * @param request request to be processed
     */
    public void execute(Request request) {
        JSONObject message = request.getMessage();
        ClientHandler clientHandler = request.getClient();
        switch (message.get("command").toString()) {
            case "setUsername" -> setUsername(lobby, message, clientHandler);
            case "getAvailableGames" -> getAvailableGames(lobby,clientHandler);
            case "setUp" -> setUpGame(lobby,message,clientHandler);
            case "join" -> joinGame(lobby,message,clientHandler);
            case "leave", "connectionLost" -> leaveLobby(lobby,clientHandler);
            default -> {
                //do nothing (request discarded)
            }
        }
    }

    private void setUsername(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        try {
            lobby.setUsername(message.get("username").toString(),clientHandler);
            clientHandler.send(LobbyMessageGenerator.usernameSetMessage(clientHandler.getUsername()));
        } catch (AlreadyTakenUsernameException e) {
             clientHandler.send(LobbyMessageGenerator.usernameAlreadyTakenMessage());
        }

    }

    private void getAvailableGames(Lobby lobby, ClientHandler clientHandler) {
        clientHandler.send(LobbyMessageGenerator.getAvailableGamesMessage(lobby.getAvailableGames()));
    }

    private void setUpGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        int numberOfPlayers = Integer.parseInt(message.get("numOfPlayers").toString());
        String gameName = message.get("gameName").toString();

        lobby.setupNewGame(numberOfPlayers,gameName,clientHandler);
        clientHandler.send(LobbyMessageGenerator.createdGameMessage());

    }

    private void joinGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        String gameName = message.get("gameName").toString();
        try {
            lobby.joinGame(clientHandler,gameName);
            clientHandler.send(LobbyMessageGenerator.joinGameMessage(gameName));
        } catch (NonExistentGameException e) {
            clientHandler.send(LobbyMessageGenerator.gameDoesNotExistMessage());
        }

    }

    private void leaveLobby(Lobby lobby, ClientHandler clientHandler) {
        lobby.leaveLobby(clientHandler);
    }

}
