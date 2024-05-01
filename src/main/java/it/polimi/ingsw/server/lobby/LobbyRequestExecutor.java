package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.util.ResponseGenerator;
import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;
import it.polimi.ingsw.util.customexceptions.NonExistentGameException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LobbyRequestExecutor {

    /**
     * method handles the incoming request from a client
     * @param lobby reference to the lobby
     * @param message message containing the request
     * @param clientHandler client handler that received the request
     */
    public static void execute(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        switch (message.get("command").toString()) {
            case "setUsername" -> setUsername(lobby, message, clientHandler);
            case "getAvailableGames" -> getAvailableGames(lobby,clientHandler);
            case "setUp" -> setUpGame(lobby,message,clientHandler);
            case "join" -> joinGame(lobby,message,clientHandler);
            case "leave" -> leaveLobby(lobby,clientHandler);
            case "connectionLost" -> leaveLobby(lobby,clientHandler);
            default -> clientHandler.reply(ResponseGenerator.response("unexpectedCommand"));
        }
    }

    private static void setUsername(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        try {
            lobby.setUsername(message.get("username").toString(),clientHandler);
            clientHandler.reply(ResponseGenerator.OKResponse());
        } catch (AlreadyTakenUsernameException e) {
             clientHandler.reply(ResponseGenerator.response("usernameAlreadyTaken"));
             //TODO sostituire la risposta qui sopra con un'apposita risposta specifica
        }

    }

    private static void getAvailableGames(Lobby lobby, ClientHandler clientHandler) {
        JSONArray games = new JSONArray();
        games.addAll(lobby.getAvailableGames().keySet());
        JSONObject response = new JSONObject();
        response.put("games", games);
        clientHandler.reply(response);
    }

    private static void setUpGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        lobby.setupNewGame(Integer.parseInt(message.get("numOfPlayers").toString()),message.get("gameName").toString(),clientHandler);
        clientHandler.reply(ResponseGenerator.OKResponse());
    }

    private static void joinGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        try {
            lobby.joinGame(clientHandler,message.get("gameName").toString());
            clientHandler.reply(ResponseGenerator.OKResponse());
        } catch (NonExistentGameException e) {
            clientHandler.reply(ResponseGenerator.response("nonexistentGame"));
            //TODO sostituire la risposta qui sopra con un'apposita risposta specifica
        }

    }

    private static void leaveLobby(Lobby lobby, ClientHandler clientHandler) {
        lobby.leaveLobby(clientHandler);
        clientHandler.reply(ResponseGenerator.OKResponse());
    }

}
