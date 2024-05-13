package it.polimi.ingsw.server.lobby;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Set;

public class LobbyMessageGenerator {

    /**
     * method creates a JSON message to be sent to the client after a successful username setting.
     * @param username username set
     * @return JSONObject
     */
    public static JSONObject usernameSetMessage(String username) {
        JSONObject message = new JSONObject();
        message.put("message", "usernameSet");
        message.put("username", username);
        return message;
    }

    /**
     * method creates a JSON message to be sent to the client when their chosen username is already in use.
     * @return JSONObject
     */
    public static JSONObject usernameAlreadyTakenMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "usernameAlreadyTaken");
        return message;
    }

    /**
     * method creates a JSON message to be sent to the client after successfully joining a game.
     * @param gameName game name set
     * @return JSONObject
     */
    public static JSONObject joinGameMessage(String gameName) {
        JSONObject message = new JSONObject();
        message.put("message", "joinGame");
        message.put("gameName", gameName);
        return message;
    }

    /**
     * method  creates a JSON message to be sent to the client after successfully creating a new game.
     * @return JSONObject
     */
    public static JSONObject createdGameMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "gameCreated");
        return message;
    }

    /**
     * method creates a JSON message to be sent to the client when they try to join a non-existent game.
     * @return JSONObject
     */
    public static JSONObject gameDoesNotExistMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "gameDoesNotExist");
        return message;
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
