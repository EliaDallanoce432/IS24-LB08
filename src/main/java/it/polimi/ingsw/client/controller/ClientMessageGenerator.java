package it.polimi.ingsw.client.controller;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for generating JSON messages that are sent to server throughout the game.
 */
public class ClientMessageGenerator {
    /**
     * generates a JSON message object to be sent to the server for setting the player's username
     * @param username The username to be set for the player.
     * @return a JSONObject representing the "setUsername" message.
     */
    public static JSONObject generateSetUsernameMessage (String username) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","setUsername");
        jsonMap.put("username",username);
        return new JSONObject(jsonMap);
    }

    /**
     * generates a JSON message object to be sent to the server for setting up a new game
     * @param gameName The desired name for the new game.
     * @param numOfPlayers The desired number of players for the game.
     * @return a JSONObject representing the "setUp" message.
     */
    public static JSONObject generateSetUpGameMessage (String gameName, int numOfPlayers) {
        JSONObject message = new JSONObject();
        message.put("command","setUp");
        message.put("gameName",gameName);
        message.put("numOfPlayers",numOfPlayers);
        return message;
    }

    /**
     * generates a JSON message object to be sent to the server for joining a specific game
     * @param gameName The name of the game to join.
     * @return a JSONObject representing the "join" message.
     */
    public static JSONObject generateJoinGameMessage (String gameName) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","join");
        jsonMap.put("gameName",gameName);
        return new JSONObject(jsonMap);
    }

    /**
     * generates a JSON message object to be sent to the server requesting a list of available games to join
     * @return a JSONObject representing the "getAvailableGames" message.
     */
    public static JSONObject generateGetAvailableGamesMessage () {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","getAvailableGames");
        return new JSONObject(jsonMap);
    }

    /**
     * generates a JSON message object to be sent to the server indicating the client is ready to begin the game
     * @return a JSONObject representing the "ready" message.
     */
    public static JSONObject generateReadyMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","ready");
        return new JSONObject(jsonMap);
    }
    /**
     * generates a JSON message object to be sent to the server informing the chosen side for a specific starter card
     * @param cardId The unique identifier of the starter card.
     * @param facingUp Whether the chosen side of the card is facing up.
     * @return a JSONObject representing the "starterCard" message.
     */
    public static JSONObject generateChosenStarterCardSideMessage(int cardId, boolean facingUp) {
        JSONObject message = new JSONObject();
        message.put("command","starterCard");
        message.put("starterCardId",cardId);
        message.put("facingUp",facingUp);
        return message;
    }

    /**
     * generates a JSON message object to be sent to the server indicating the chosen secret objective card
     * @param id The unique identifier of the chosen secret objective card.
     * @return a JSONObject representing the "objectiveCard" message.
     */
    public static JSONObject generateChosenSecretObjectiveMessage(int id) {
        JSONObject message = new JSONObject();
        message.put("command","objectiveCard");
        message.put("objectiveCardId",id);
        return message;
    }

    /**
     * generates a JSON message object to be sent to the server for placing a card on the game board
     * @param cardId The unique identifier of the card to be placed.
     * @param x The X-coordinate of the desired placement location.
     * @param y The Y-coordinate of the desired placement location.
     * @param facingUp Whether the card should be placed facing up or down.
     * @return a JSONObject representing the "place" message.
     */
    public static JSONObject generatePlaceMessage(int cardId, int x, int y, boolean facingUp) {
        JSONObject message = new JSONObject();
        message.put("command","place");
        message.put("placeableCardId",cardId);
        message.put("x",x);
        message.put("y",y);
        message.put("facingUp",facingUp);
        return message;
    }
    /**
     * generates a JSON message object to be sent to the server requesting to draw a resource card directly from the resource deck.
     * @return A JSONObject representing the "directDrawResourceCard" message.
     */
    public static JSONObject generateDirectDrawResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","directDrawResourceCard");
        return new JSONObject(jsonMap);
    }

    /**
     * generates a JSON message object to be sent to the server requesting to draw a resource card from the left side of the resource deck.
     * @return A JSONObject representing the "drawLeftResourceCard" message.
     */
    public static JSONObject generateDrawLeftResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawLeftResourceCard");
        return new JSONObject(jsonMap);
    }

    /**
     * generates a JSON message object to be sent to the server requesting to draw a resource card from the right side of the resource deck.
     * @return A JSONObject representing the "drawRightResourceCard" message.
     */
    public static JSONObject generateDrawRightResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawRightResourceCard");
        return new JSONObject(jsonMap);
    }

    /**
     * generates a JSON message object to be sent to the server requesting to draw a gold card directly from the gold deck.
     * @return A JSONObject representing the "directDrawGoldCard" message.
     */
    public static JSONObject generateDirectDrawGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","directDrawGoldCard");
        return new JSONObject(jsonMap);
    }
    /**
     * generates a JSON message object to be sent to the server requesting to draw a gold card from the left side of the gold deck.
     * @return A JSONObject representing the "drawLeftGoldCard" message.
     */
    public static JSONObject generateDrawLeftGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawLeftGoldCard");
        return new JSONObject(jsonMap);
    }
    /**
     * generates a JSON message object to be sent to the server requesting to draw a gold card from the right side of the gold deck.
     * @return A JSONObject representing the "drawRightGoldCard" message.
     */
    public static JSONObject generateDrawRightGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawRightGoldCard");
        return new JSONObject(jsonMap);
    }

    /**
     * generates a JSON message object to be sent to the server indicating the client intends to leave the current game session.
     * @return A JSONObject representing the "leave" message.
     */
    public static JSONObject generateLeaveMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","leave");
        return new JSONObject(jsonMap);
    }
}
