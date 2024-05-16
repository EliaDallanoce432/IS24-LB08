package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * This class is responsible for generating JSON messages that are sent to clients throughout the game.
 * These messages inform players about game state, their hand and deck information, and other relevant game events.
 */
public class ServerMessageGenerator {

    private final Game game;

    public ServerMessageGenerator(Game game) {
        this.game = game;
    }



    /**
     * this message is sent to each player before the beginning of the match so that they can select the starter card orientation and their
     * secrete objective
     * @param starterCard given to the player
     * @param objectiveCard1 first objective card that can be selected
     * @param objectiveCard2 second objective card that can be selected
     * @return a message containing these three cards
     */
    public JSONObject cardsSelectionMessage (StarterCard starterCard, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("message", "cardsSelection");
        jsonMap.put("starterCardID", String.valueOf(starterCard.getId()));
        jsonMap.put("objectiveCardID1", String.valueOf(objectiveCard1.getId()));
        jsonMap.put("objectiveCardID2", String.valueOf(objectiveCard2.getId()));
        return new JSONObject(jsonMap);
    }

    /**
     * this message is sent to each player at the beginning of the match
     * @param gameController of the current match
     * @param player player who is playing the current match
     * @return a message containing all the necessary information the player needs to start the match
     */
    public JSONObject startGameMessage (GameController gameController, Player player) {
        JSONObject message = new JSONObject();
        message.put("message","startGame");
        message.put("hand", updatedHand(player));
        message.put("decks", updatedDecks());
        message.put("placementHistory", updatedPlacementHistory(player));
        message.put("resources", updatedResources(player));
        message.put("secretObjectiveID", String.valueOf(player.getSecretObjective().getId()));
        message.put("token", player.getToken().toString());
        message.put("commonObjective1", String.valueOf(game.getCommonObjectives().getFirst().getId()));
        message.put("commonObjective2", String.valueOf(game.getCommonObjectives().getLast().getId()));
        message.put("firstPlayer", gameController.getTurnPlayerUsername());
        return message;
    }

    /**
     * this message sends to a player his updated hand
     * @param player with the updated hand
     * @return a message containing the updated hand
     */
    public JSONObject updatedHandMessage(Player player) {
        JSONObject message = new JSONObject();
        message.put("message", "updatedHand");
        message.put("updatedHand", updatedHand(player));
        return message;
    }
    /**
     * this message is sent to notify the beginning of a player's turn
     * @param gameController is used to get the player's name
     * @return a message showing which player is about to play and his name
     */
    public JSONObject turnPlayerUpdateMessage(GameController gameController) {
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("message", "turnPlayerUpdate");
        jsonMap.put("player",  gameController.getTurnPlayerUsername());
        return new JSONObject(jsonMap);
    }

    /**
     * this message is sent to players in order to update the decks after each draw
     * @return a message containing updated information about the decks
     */
    public JSONObject updatedDecksMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "updatedDecks");
        message.put("updatedDecks", updatedDecks());
        return message;
    }

    /**
     * this message is sent when the player executes a successful placement
     * @param player who places the card
     * @return a message containing updated information (score, his hand, resources on the game-field)
     */
    public JSONObject successfulPlaceMessage(Player player) {
        JSONObject message = new JSONObject();
        message.put("message","successfulPlace");
        message.put("placementHistory", updatedPlacementHistory(player));
        message.put("updatedHand" , updatedHand(player));
        message.put("updatedResources", updatedResources(player));
        message.put("updatedScore", String.valueOf(player.getScore()));
        return message;
    }

    /**
     * this message is sent when a player cannot place a card for a particular reason
     * @param reason explains why the placement is incorrect
     * @return the message to the player
     */
    public JSONObject cannotPlaceMessage(String reason) {
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("message", "cannotPlace");
        jsonMap.put("reason",  reason);
        return new JSONObject(jsonMap);
    }

    /**
     * this message is sent to notify players' updated scores after each turn
     * @param gameController of the current game
     * @return a message containing all the players' scores
     */
    public JSONObject updatedScoresMessage (GameController gameController) {
        JSONObject message = new JSONObject();
        message.put("message","updatedScores");
        JSONArray scores = new JSONArray();
        for(ClientHandler clientHandler: gameController.getClientHandlers()) {
            JSONObject client = new JSONObject();
            client.put("username", clientHandler.getUsername());
            client.put("score", String.valueOf(gameController.getCurrentPlayer(clientHandler).getScore()));
            scores.add(client);
        }
        message.put("updatedScores", scores);
        return message;
    }

    /**
     * this message informs the players that they're playing the last round
     * @param reason what triggered the last round
     * @return the message that informs the players
     */
    public JSONObject lastRoundMessage(String reason) {
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("message", "lastRound");
        jsonMap.put("reason",  reason);
        return new JSONObject(jsonMap);
    }

    /**
     * this message sends to the players their final scores when the game is ended
     * @return the final scores
     */
    public JSONObject leaderBoardMessage (GameController gameController, ArrayList<ClientHandler> clientHandlers) {
        JSONObject message = new JSONObject();
        message.put("message", "leaderBoard");
        if(!clientHandlers.isEmpty()) {
            Map<String,String> jsonMap = new HashMap<>();
            jsonMap.put("username",clientHandlers.getFirst().getUsername());
            jsonMap.put("score", String.valueOf( gameController.getCurrentPlayer(clientHandlers.getFirst()).getScore()));
            jsonMap.put("solvedObjectives" , String.valueOf(gameController.getCurrentPlayer(clientHandlers.getFirst()).getNumOfCompletedObjectiveCards()));
            JSONObject player = new JSONObject(jsonMap);
            message.put("first", player);
        }
        else message.put("first", null);
        if(clientHandlers.size()>= 2) {
            Map<String,String> jsonMap = new HashMap<>();
            jsonMap.put("username",clientHandlers.get(1).getUsername());
            jsonMap.put("score", String.valueOf( gameController.getCurrentPlayer(clientHandlers.get(1)).getScore()));
            jsonMap.put("solvedObjectives" , String.valueOf(gameController.getCurrentPlayer(clientHandlers.get(1)).getNumOfCompletedObjectiveCards()));
            JSONObject player = new JSONObject(jsonMap);
            message.put("second", player);
        }
        else message.put("second", null);

        if(clientHandlers.size()>= 3) {
            Map<String,String> jsonMap = new HashMap<>();
            jsonMap.put("username",clientHandlers.get(2).getUsername());
            jsonMap.put("score", String.valueOf( gameController.getCurrentPlayer(clientHandlers.get(2)).getScore()));
            jsonMap.put("solvedObjectives" , String.valueOf(gameController.getCurrentPlayer(clientHandlers.get(2)).getNumOfCompletedObjectiveCards()));
            JSONObject player = new JSONObject(jsonMap);
            message.put("third", player);
        }
        else message.put("third", null);
        if(clientHandlers.size()>= 4) {
            Map<String,String> jsonMap = new HashMap<>();
            jsonMap.put("message", clientHandlers.get(3).getUsername());
            jsonMap.put("score",  String.valueOf( gameController.getCurrentPlayer(clientHandlers.get(3)).getScore()));
            jsonMap.put("solvedObjectives", String.valueOf(gameController.getCurrentPlayer(clientHandlers.get(3)).getNumOfCompletedObjectiveCards()));
            JSONObject player = new JSONObject(jsonMap);
            message.put("fourth", player);
        }
        else message.put("fourth", null);
        return message;
    }

    /**
     * message sent when a client loses connection to inform the other clients that the game is getting cancelled
     * @return message
     */
    public JSONObject closingGameMessage () {
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("message", "closingGame");
        return new JSONObject(jsonMap);
    }

    private JSONArray updatedPlacementHistory(Player player) {
        JSONArray placementHistory = new JSONArray();
        System.out.println(player.getGamefield().getPlacementHistory());
        for(PlaceableCard placeableCard : player.getGamefield().getPlacementHistory()) {
            Map<String,String> jsonMap = new HashMap<>();
            jsonMap.put("cardID", String.valueOf(placeableCard.getId()));
            jsonMap.put("facingUp", String.valueOf(placeableCard.isFacingUp()));
            jsonMap.put("x", String.valueOf(placeableCard.getX()));
            jsonMap.put("y", String.valueOf(placeableCard.getY()));
            JSONObject card = new JSONObject(jsonMap);
            placementHistory.add(card);
        }
        return placementHistory;
    }

    private JSONArray updatedHand(Player player) {
        JSONArray handArray = new JSONArray();
        for(PlaceableCard cardInHand : player.getHand()){
            System.out.println(cardInHand.getId());
            handArray.add(String.valueOf(cardInHand.getId()));
        }
        return handArray;
    }

    private JSONObject updatedDecks() {
        Map<String,String> decks = new HashMap<>();
        decks.put("topDeckResourceCardID", String.valueOf(game.getResourceCardDeck().getTopCardID()));
        decks.put("leftRevealedResourceCardID", String.valueOf(game.getResourceCardDeck().getLeftRevealedCardID()));
        decks.put("rightRevealedResourceCardID", String.valueOf(game.getResourceCardDeck().getRightRevealedCardID()));
        decks.put("topDeckGoldCardID", String.valueOf(game.getGoldCardDeck().getTopCardID()));
        decks.put("leftRevealedGoldCardID", String.valueOf(game.getGoldCardDeck().getLeftRevealedCardID()));
        decks.put("rightRevealedGoldCardID", String.valueOf(game.getGoldCardDeck().getRightRevealedCardID()));
        return new JSONObject(decks);
    }

    private JSONObject updatedResources(Player player) {


        Map<String,String> jsonMap= new HashMap<>();

        jsonMap.put("fungiResources", String.valueOf(player.getGamefield().getFungiCount()));
        jsonMap.put("plantResources", String.valueOf(player.getGamefield().getPlantCount()));
        jsonMap.put("animalResources", String.valueOf(player.getGamefield().getAnimalCount()));
        jsonMap.put("insectResources", String.valueOf(player.getGamefield().getInsectCount()));
        jsonMap.put("featherCount", String.valueOf(player.getGamefield().getFeatherCount()));
        jsonMap.put("scrollCount", String.valueOf(player.getGamefield().getScrollCount()));
        jsonMap.put("inkPotCount", String.valueOf(player.getGamefield().getInkPotCount()));

        return new JSONObject(jsonMap);
    }
}
