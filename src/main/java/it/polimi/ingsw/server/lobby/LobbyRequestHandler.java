package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;
import it.polimi.ingsw.util.customexceptions.CannotCreateGameException;
import it.polimi.ingsw.util.customexceptions.GameIsFullException;
import it.polimi.ingsw.util.customexceptions.NonExistentGameException;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

/**
 * This class represents the request parser of the exchanged messages.
 */
public class LobbyRequestHandler {
    private final Lobby lobby;

    public LobbyRequestHandler(Lobby lobby) {
        this.lobby = lobby;
    }
    /**
     * Handles the incoming request from a client
     * @param request request to be processed
     */
    public void execute(Request request) {
        JSONObject message = request.message();
        ClientHandler clientHandler = request.client();
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

    /**
     * Handles the logic for setting a username for a client.
     * @param lobby lobby reference
     * @param message json object message
     * @param clientHandler client handler of client
     */
    private void setUsername(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        try {
            lobby.setUsername(message.get("username").toString(),clientHandler);
            clientHandler.send(LobbyMessageGenerator.usernameSetMessage(clientHandler.getUsername()));
        } catch (AlreadyTakenUsernameException e) {
             clientHandler.send(LobbyMessageGenerator.usernameAlreadyTakenMessage());
        }

    }

    /**
     * retrieves a list of available games from the lobby and sends them to the requesting client
     * @param lobby lobby reference
     * @param clientHandler client handler of client
     */
    private void getAvailableGames(Lobby lobby, ClientHandler clientHandler) {
        clientHandler.send(LobbyMessageGenerator.getAvailableGamesMessage(lobby.getAvailableGames()));
    }

    /**
     * processes a request to create a new game
     * @param lobby lobby reference
     * @param message json object message
     * @param clientHandler client handler of client
     */
    private void setUpGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        int numberOfPlayers = Integer.parseInt(message.get("numOfPlayers").toString());
        String gameName = message.get("gameName").toString();

        try {
            lobby.setupNewGame(numberOfPlayers,gameName,clientHandler);
            clientHandler.send(LobbyMessageGenerator.createdGameMessage());
        } catch (CannotCreateGameException e) {
            clientHandler.send(LobbyMessageGenerator.cannotCreateGameMessage(e.getMessage()));
        }
    }

    /**
     * handles a client's request to join a game
     * @param lobby lobby reference
     * @param message json object message
     * @param clientHandler client handler of client
     */
    private void joinGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        String gameName = message.get("gameName").toString();
        try {
            lobby.joinGame(clientHandler,gameName);
            clientHandler.send(LobbyMessageGenerator.joinGameMessage(gameName));
        } catch (NonExistentGameException e) {
            clientHandler.send(LobbyMessageGenerator.gameDoesNotExistMessage());
        } catch (GameIsFullException e) {
            clientHandler.send(LobbyMessageGenerator.gameIsFullMessage());
        }

    }

    /**
     * removes a client from the lobby when they choose to leave
     * @param lobby lobby reference
     * @param clientHandler client handler of client
     */
    private void leaveLobby(Lobby lobby, ClientHandler clientHandler) {
        lobby.leaveLobby(clientHandler);
    }

}
