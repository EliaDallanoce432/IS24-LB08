package it.polimi.ingsw.client.controller;

import org.json.simple.JSONObject;

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

    public static JSONObject generateReadyMessage() {
        JSONObject message = new JSONObject();
        message.put("command","ready");
        return message;
    }

    public static JSONObject generateChosenStarterCardOrientationMessage(boolean facingUp) {
        JSONObject message = new JSONObject();
        message.put("command","starterCard");
        message.put("facingUp",facingUp);
        return message;
    }

    public static JSONObject generateChosenSecretObjectiveMessage(int id) {
        JSONObject message = new JSONObject();
        message.put("command","objectiveCard");
        message.put("id",id);
        return message;
    }

    public static JSONObject generatePlaceMessage(int cardId, int x, int y) {
        JSONObject message = new JSONObject();
        message.put("command","place");
        message.put("cardId",cardId);
        message.put("x",x);
        message.put("y",y);
        return message;
    }

    public static JSONObject generateDirectDrawResourceCardMessage() {
        JSONObject message = new JSONObject();
        message.put("command","directDrawResourceCard");
        return message;
    }

    public static JSONObject generateDrawLeftResourceCardMessage() {
        JSONObject message = new JSONObject();
        message.put("command","drawLeftResourceCard");
        return message;
    }

    public static JSONObject generateDrawRightResourceCardMessage() {
        JSONObject message = new JSONObject();
        message.put("command","drawRightResourceCard");
        return message;
    }

    public static JSONObject generateDirectDrawGoldCardMessage() {
        JSONObject message = new JSONObject();
        message.put("command","directDrawGoldCard");
        return message;
    }

    public static JSONObject generateDrawLeftGoldCardMessage() {
        JSONObject message = new JSONObject();
        message.put("command","drawLeftGoldCard");
        return message;
    }

    public static JSONObject generateDrawRightGoldCardMessage() {
        JSONObject message = new JSONObject();
        message.put("command","drawRightGoldCard");
        return message;
    }

    public static JSONObject generateLeaveMessage() {
        JSONObject message = new JSONObject();
        message.put("command","leave");
        return message;
    }
}
