package it.polimi.ingsw.network;

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
            default -> clientHandler.sendMessage(ResponseGenerator.generateResponse("unexpectedCommand"));
        }
    }

    private static void setUsername(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        try {
            lobby.setUsername(message.get("parameters").toString(),clientHandler);
        } catch (AlreadyTakenUsernameException e) {
             clientHandler.sendMessage(ResponseGenerator.generateResponse("usernameAlreadyTaken"));
        }
        clientHandler.sendMessage(ResponseGenerator.generateResponse("ok"));
    }

    private static void getAvailableGames(Lobby lobby, ClientHandler clientHandler) {
        JSONArray games = new JSONArray();
        games.addAll(lobby.getAvailableGames().keySet());
        JSONObject response = new JSONObject();
        response.put("games", games);
        clientHandler.sendMessage(response);
    }

    private static void setUpGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        JSONObject parameters = new JSONObject();
        JSONArray parametersArray = (JSONArray) parameters.get("parameters");
        JSONObject numberOfPLayers = (JSONObject) parametersArray.getFirst();
        JSONObject gameName = (JSONObject) parametersArray.getLast();

        lobby.setupNewGame(((Long) numberOfPLayers.get("numberOfPlayers")).intValue(),gameName.get("gameName").toString(),clientHandler);
        clientHandler.sendMessage(ResponseGenerator.generateResponse("ok"));
    }

    private static void joinGame(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        try {
            lobby.joinGame(clientHandler,message.get("gameName").toString());
        } catch (NonExistentGameException e) {
            clientHandler.sendMessage(ResponseGenerator.generateResponse("nonexistentGame"));
        }
        clientHandler.sendMessage(ResponseGenerator.generateResponse("ok"));
    }

    private static void leaveLobby(Lobby lobby, ClientHandler clientHandler) {
        lobby.leaveLobby(clientHandler);
        clientHandler.sendMessage(ResponseGenerator.generateResponse("ok"));
    }

}
