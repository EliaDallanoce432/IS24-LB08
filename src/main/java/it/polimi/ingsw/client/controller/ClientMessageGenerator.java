package it.polimi.ingsw.client.controller;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientMessageGenerator {
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

    public static JSONObject generateSetUpGameMessage (String gameName, int numOfPlayers) {
        JSONObject message = new JSONObject();
        message.put("command","setUp");
        message.put("gameName",gameName);
        message.put("numOfPlayers",numOfPlayers);
        return message;
    }

    public static JSONObject generateJoinGameMessage (String gameName) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","join");
        jsonMap.put("gameName",gameName);
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateGetAvailableGamesMessage () {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","getAvailableGames");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateReadyMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","ready");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateChosenStarterCardSideMessage(int cardId, boolean facingUp) {
        JSONObject message = new JSONObject();
        message.put("command","starterCard");
        message.put("starterCardId",cardId);
        message.put("facingUp",facingUp);
        return message;
    }

    public static JSONObject generateChosenSecretObjectiveMessage(int id) {
        JSONObject message = new JSONObject();
        message.put("command","objectiveCard");
        message.put("objectiveCardId",id);
        return message;
    }

    public static JSONObject generatePlaceMessage(int cardId, int x, int y, boolean facingUp) {
        JSONObject message = new JSONObject();
        message.put("command","place");
        message.put("placeableCardId",cardId);
        message.put("x",x);
        message.put("y",y);
        message.put("facingUp",facingUp);
        return message;
    }

    public static JSONObject generateDirectDrawResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","directDrawResourceCard");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateDrawLeftResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawLeftResourceCard");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateDrawRightResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawRightResourceCard");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateDirectDrawGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","directDrawGoldCard");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateDrawLeftGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawLeftGoldCard");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateDrawRightGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawRightGoldCard");
        return new JSONObject(jsonMap);
    }

    public static JSONObject generateLeaveMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","leave");
        return new JSONObject(jsonMap);
    }
}
