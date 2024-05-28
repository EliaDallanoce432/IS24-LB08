package it.polimi.ingsw.client.controller;

import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for generating JSON messages that are sent to server throughout the game.
 */
public class ClientMessageGenerator {
    /**
     * Generates a JSON message object to be sent to the server for setting the player's username.
     * @param username The username to be set for the player.
     * @return The JSONObject representing the "setUsername" message.
     */
    public static JSONObject generateSetUsernameMessage (String username) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","setUsername");
        jsonMap.put("username",username);
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server to set up a new game.
     * @param gameName The desired name for the new game.
     * @param numOfPlayers The desired number of players for the game.
     * @return The JSONObject representing the "setUp" message.
     */
    public static JSONObject generateSetUpGameMessage (String gameName, int numOfPlayers) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","setUp");
        jsonMap.put("gameName",gameName);
        jsonMap.put("numOfPlayers",String.valueOf(numOfPlayers));
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server to join a specific game.
     * @param gameName The name of the game to join.
     * @return The JSONObject representing the "join" message.
     */
    public static JSONObject generateJoinGameMessage (String gameName) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","join");
        jsonMap.put("gameName",gameName);
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server requesting a list of available games to join.
     * @return The JSONObject representing the "getAvailableGames" message.
     */
    public static JSONObject generateGetAvailableGamesMessage () {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","getAvailableGames");
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server indicating the client is ready to begin the game.
     * @return The JSONObject representing the "ready" message.
     */
    public static JSONObject generateReadyMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","ready");
        return new JSONObject(jsonMap);
    }
    /**
     * Generates a JSON message object to be sent to the server with the chosen side for the starter card.
     * @param cardId The unique identifier of the starter card.
     * @param facingUp Whether the chosen side of the card is facing up. True if the front is selected, false otherwise.
     * @return The JSONObject representing the "starterCard" message.
     */
    public static JSONObject generateChosenStarterCardSideMessage(int cardId, boolean facingUp) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","starterCard");
        jsonMap.put("starterCardId",String.valueOf(cardId));
        jsonMap.put("facingUp", String.valueOf(facingUp));
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server indicating the chosen secret objective card.
     * @param id The unique identifier of the chosen secret objective card.
     * @return The JSONObject representing the "objectiveCard" message.
     */
    public static JSONObject generateChosenSecretObjectiveMessage(int id) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","objectiveCard");
        jsonMap.put("objectiveCardId", String.valueOf(id));
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server for placing a card on the game board.
     * @param cardId The unique identifier of the card to be placed.
     * @param x The X-coordinate of the desired placement location.
     * @param y The Y-coordinate of the desired placement location.
     * @param facingUp Whether the card should be placed facing up or down.
     * @return The JSONObject representing the "place" message.
     */
    public static JSONObject generatePlaceMessage(int cardId, int x, int y, boolean facingUp) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","place");
        jsonMap.put("placeableCardId",String.valueOf(cardId));
        jsonMap.put("x", String.valueOf(x));
        jsonMap.put("y", String.valueOf(y));
        jsonMap.put("facingUp", String.valueOf(facingUp));
        return new JSONObject(jsonMap);
    }
    /**
     * Generates a JSON message object to be sent to the server requesting to draw a resource card directly from the resource deck.
     * @return The JSONObject representing the "directDrawResourceCard" message.
     */
    public static JSONObject generateDirectDrawResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","directDrawResourceCard");
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server requesting to draw the left revealed card of the resource deck.
     * @return The JSONObject representing the "drawLeftResourceCard" message.
     */
    public static JSONObject generateDrawLeftResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawLeftResourceCard");
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server requesting to draw the right revealed card of the resource deck.
     * @return The JSONObject representing the "drawRightResourceCard" message.
     */
    public static JSONObject generateDrawRightResourceCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawRightResourceCard");
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server requesting to draw a gold card directly from the gold deck.
     * @return The JSONObject representing the "directDrawGoldCard" message.
     */
    public static JSONObject generateDirectDrawGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","directDrawGoldCard");
        return new JSONObject(jsonMap);
    }
    /**
     * Generates a JSON message object to be sent to the server requesting to draw the left revealed card of the gold deck.
     * @return The JSONObject representing the "drawLeftGoldCard" message.
     */
    public static JSONObject generateDrawLeftGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawLeftGoldCard");
        return new JSONObject(jsonMap);
    }
    /**
     * Generates a JSON message object to be sent to the server requesting to draw the right revealed card of the gold deck.
     * @return The JSONObject representing the "drawRightGoldCard" message.
     */
    public static JSONObject generateDrawRightGoldCardMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","drawRightGoldCard");
        return new JSONObject(jsonMap);
    }

    /**
     * Generates a JSON message object to be sent to the server indicating the client intends to leave the current game session.
     * @return The JSONObject representing the "leave" message.
     */
    public static JSONObject generateLeaveMessage() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("command","leave");
        return new JSONObject(jsonMap);
    }
}