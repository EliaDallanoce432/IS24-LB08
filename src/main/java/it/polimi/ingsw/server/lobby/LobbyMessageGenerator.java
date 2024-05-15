package it.polimi.ingsw.server.lobby;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LobbyMessageGenerator {

    /**
     * method creates a JSON message to be sent to the client after a successful username setting.
     * @param username username set
     * @return JSONObject
     */
    public static JSONObject usernameSetMessage(String username) {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "usernameSet");
        jsonMap.put("username", username);
        return new JSONObject(jsonMap);
    }

    /**
     * method creates a JSON message to be sent to the client when their chosen username is already in use.
     * @return JSONObject
     */
    public static JSONObject usernameAlreadyTakenMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "usernameAlreadyTaken");
        return new JSONObject(jsonMap);
    }

    /**
     * method creates a JSON message to be sent to the client after successfully joining a game.
     * @param gameName game name set
     * @return JSONObject
     */
    public static JSONObject joinGameMessage(String gameName) {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "joinGame");
        jsonMap.put("gameName", gameName);
        return new JSONObject(jsonMap);
    }

    /**
     * method  creates a JSON message to be sent to the client after successfully creating a new game.
     * @return JSONObject
     */
    public static JSONObject createdGameMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "gameCreated");
        return new JSONObject(jsonMap);
    }

    /**
     * method creates a JSON message to be sent to the client when they try to join a non-existent game.
     * @return JSONObject
     */
    public static JSONObject gameDoesNotExistMessage() {
        Map<String, String> jsonMap= new HashMap<>();
        jsonMap.put("message", "gameDoesNotExist");
        return new JSONObject(jsonMap);
    }

    /**
     * method creates a JSON message containing a list of available games
     * @param gameNames game names available
     * @return JSONObject
     */
    public static JSONObject getAvailableGamesMessage(Set<String> gameNames) {
        JSONObject message = new JSONObject();
        message.put("message", "availableGames");
        JSONArray games = new JSONArray();
        for (String gameName : gameNames) {
            JSONObject game = new JSONObject();
            game.put("name", gameName);
            games.add(game);
        }
        message.put("games", games);
        return message;
    }
}
