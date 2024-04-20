package it.polimi.ingsw.client.controller;

import org.json.simple.JSONObject;

public class MessageGenerator {
    public static JSONObject generateSetUsernameMessage (String Username) {
        JSONObject message = new JSONObject();
        message.put("command","setUsername");
        message.put("username",Username);
        return message;
    }

    public static JSONObject generateLeaveLobbyMessage () {
        JSONObject message = new JSONObject();
        message.put("command","leave");
        return message;
    }

    public static JSONObject generateSetUpGameMessage (String gamename, int numofplayers) {
        JSONObject message = new JSONObject();
        message.put("command","setUp");
        message.put("gameName",gamename);
        message.put("numOfPlayers",numofplayers);
        return message;
    }

    public static JSONObject generateJoinGameMessage (String gamename) {
        JSONObject message = new JSONObject();
        message.put("command","join");
        message.put("gameName",gamename);
        return message;
    }

    public static JSONObject generateGetAvailableGamesMessage () {
        JSONObject message = new JSONObject();
        message.put("command","getAvailableGames");
        return message;
    }
}
