package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.server.controller.GameController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the generator of messages exchanged between a player and the lobby
 */
public class LobbyMessageGenerator {

    /**
     * creates a message to be sent after a successful join in a lobby.
     * @return JSON message
     */
    public static JSONObject joinedLobbyMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "joinedLobby");
        return new JSONObject(jsonMap);
    }

    /**
     * creates a JSON message to be sent to the client after a successful username setting.
     * @param username username set
     * @return JSON message
     */
    public static JSONObject usernameSetMessage(String username) {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "usernameSet");
        jsonMap.put("username", username);
        return new JSONObject(jsonMap);
    }

    /**
     * creates a JSON message to be sent to the client when their chosen username is already in use.
     * @return JSON message
     */
    public static JSONObject usernameAlreadyTakenMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "usernameAlreadyTaken");
        return new JSONObject(jsonMap);
    }

    /**
     * creates a JSON message to be sent to the client after successfully joining a game.
     * @param gameName game name set
     * @return JSON message
     */
    public static JSONObject joinGameMessage(String gameName) {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "joinGame");
        jsonMap.put("gameName", gameName);
        return new JSONObject(jsonMap);
    }

    /**
     * creates a JSON message to be sent to the client after successfully creating a new game.
     * @return JSON message
     */
    public static JSONObject createdGameMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "gameCreated");
        return new JSONObject(jsonMap);
    }

    public static JSONObject cannotCreateGameMessage(String reason) {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "cannotCreateGame");
        jsonMap.put("reason", reason);
        return new JSONObject(jsonMap);
    }

    /**
     * creates a JSON message to be sent to the client when they try to join a non-existent game.
     * @return JSON message
     */
    public static JSONObject gameDoesNotExistMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "gameDoesNotExist");
        return new JSONObject(jsonMap);
    }

    public static JSONObject gameIsFullMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "gameIsFull");
        return new JSONObject(jsonMap);
    }

    /**
     * creates a JSON message containing a list of available games
     * @param availableGames available games for joining
     * @return JSON message
     */
    public static JSONObject getAvailableGamesMessage(HashMap<String, GameController> availableGames) {
        JSONObject message = new JSONObject();
        message.put("message", "availableGames");
        JSONArray games = new JSONArray();
        for (String availableGameName : availableGames.keySet()) {
            JSONObject game = new JSONObject();
            game.put("nameAndPlayers", availableGameName + " - (" + availableGames.get(availableGameName).getClientHandlers().size() + "/" + availableGames.get(availableGameName).getNumberOfPlayers() + ")");
            games.add(game);
        }
        message.put("games", games);
        return message;
    }
}
