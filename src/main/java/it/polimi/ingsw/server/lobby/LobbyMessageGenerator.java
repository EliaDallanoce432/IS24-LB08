package it.polimi.ingsw.server.lobby;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Set;

public class LobbyMessageGenerator {

    public static JSONObject usernameSetMessage(String username) {
        JSONObject message = new JSONObject();
        message.put("message", "usernameSet");
        message.put("username", username);
        return message;
    }

    public static JSONObject usernameAlreadyTakenMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "usernameAlreadyTaken");
        return message;
    }

    public static JSONObject joinGameMessage(String gameName) {
        JSONObject message = new JSONObject();
        message.put("message", "joinGame");
        message.put("gameName", gameName);
        return message;
    }

    public static JSONObject gameDoesNotExistMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "gameDoesNotExistMessage");
        return message;
    }

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
