package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ServerMessageGenerator {


    public static JSONObject cardsSelectionMessage (StarterCard startercard, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        JSONObject message = new JSONObject();
        message.put("message", "cardsSelection");
        message.put("starterCardID", String.valueOf(startercard.getId()));
        message.put("objectiveCardID1", String.valueOf(objectiveCard1.getId()));
        message.put("objectiveCardID2", String.valueOf(objectiveCard2.getId()));
        return message;
    }

    public static JSONObject startGameMessage (GameController gameController, Player player) {
        JSONObject message = new JSONObject();
        message.put("message","startGame");
        message.put("hand", updatedHand(player));
        message.put("decks", updatedDecks());
        message.put("placementHistory", updatedPlacementHistory(player));
        message.put("secretObjectiveID", String.valueOf(player.getSecretObjective().getId()));
        message.put("token", player.getToken().toString());
        message.put("commonObjective1", String.valueOf(Game.getInstance().commonObjectives.getFirst().getId()));
        message.put("commonObjective2", String.valueOf(Game.getInstance().commonObjectives.getLast().getId()));
        message.put("firstPlayer", gameController.getTurnPlayerUsername());
        return message;
    }

    public static JSONObject turnPlayerUpdateMessage(GameController gameController) {
        JSONObject message = new JSONObject();
        message.put("message","turnPlayerUpdate");
        message.put("player", gameController.getTurnPlayerUsername());
        return message;
    }

    public static JSONObject updatedHandMessage(Player player) {
        JSONObject message = new JSONObject();
        message.put("message","updatedHand");
        message.put("updatedHand",updatedHand(player));
        return message;
    }

    public static JSONObject updatedDecksMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "updatedDecks");
        message.put("updatedDecks", updatedDecks());
        return message;
    }

    public static JSONObject successfulPlaceMessage(Player player) {
        JSONObject message = new JSONObject();
        message.put("message","successfulPlace");
        message.put("placementHistory", updatedPlacementHistory(player));
        message.put("updatedHand" , updatedHand(player));
        message.put("updatedResources", updatedResources(player));
        message.put("updatedScore", String.valueOf(player.getScore()));
        return message;
    }

    public static JSONObject cannotPlaceMessage(String reason) {
        JSONObject message = new JSONObject();
        message.put("message","cannotPlace");
        message.put("reason", reason);
        return message;
    }

    public static JSONObject updatedScoreMessage (ArrayList<String> names,ArrayList<Integer> updatedScores) {
        JSONObject message = new JSONObject();
        JSONArray scoreArray = new JSONArray();
        message.put("message","updatedScore");
        for (int i=0; i < names.size(); i++) {
            JSONObject personalScore = new JSONObject();
            personalScore.put("username", names.get(i));
            personalScore.put("score", updatedScores.get(i));
            scoreArray.add(personalScore);
        }
        message.put("scoreArray", scoreArray);
        return message;
    }

    //TODO leaderboard message

    /**
     * message sent when a client looses connection to inform the other clients that the game is getting cancelled
     * @return message
     */
    public static JSONObject closingGameMessage () {
        JSONObject message = new JSONObject();
        message.put("message","closingGame");
        return message;
    }

    private static JSONArray updatedPlacementHistory(Player player) {
        JSONArray placementHistory = new JSONArray();
        System.out.println(player.getGamefield().getPlacementHistory());
        for(PlaceableCard placeableCard : player.getGamefield().getPlacementHistory()) {
            JSONObject card = new JSONObject();
            card.put("cardID", String.valueOf(placeableCard.getId()));
            card.put("facingUp", String.valueOf(placeableCard.isFacingUp()));
            card.put("x", String.valueOf(placeableCard.getX()));
            card.put("y", String.valueOf(placeableCard.getY()));
            placementHistory.add(card);
        }
        return placementHistory;
    }

    private static JSONArray updatedHand(Player player) {
        JSONArray handArray = new JSONArray();
        for(PlaceableCard cardInHand : player.getHand()){
            System.out.println(cardInHand.getId());
            handArray.add(String.valueOf(cardInHand.getId()));
        }
        return handArray;
    }

    private static JSONObject updatedDecks() {
        Game game = Game.getInstance();
        JSONObject decks = new JSONObject();
        decks.put("topDeckResourceCardID", String.valueOf(game.resourceCardDeck.getTopCardID()));
        decks.put("leftRevealedResourceCardID", String.valueOf(game.resourceCardDeck.getLeftRevealedCardID()));
        decks.put("rightRevealedResourceCardID", String.valueOf(game.resourceCardDeck.getRightRevealedCardID()));
        decks.put("topDeckGoldCardID", String.valueOf(game.goldCardDeck.getTopCardID()));
        decks.put("leftRevealedGoldCardID", String.valueOf(game.goldCardDeck.getLeftRevealedCardID()));
        decks.put("rightRevealedGoldCardID", String.valueOf(game.goldCardDeck.getRightRevealedCardID()));
        return decks;
    }

    private static JSONArray updatedResources(Player player) {
        JSONArray updatedResources = new JSONArray();
        JSONObject fungiResources = new JSONObject();
        fungiResources.put("fungiResources", String.valueOf(player.getGamefield().getFungiCount()));
        JSONObject plantResources = new JSONObject();
        plantResources.put("plantResources", String.valueOf(player.getGamefield().getPlantCount()));
        JSONObject animalResources = new JSONObject();
        animalResources.put("animalResources", String.valueOf(player.getGamefield().getAnimalCount()));
        JSONObject insectResources = new JSONObject();
        insectResources.put("insectResources", String.valueOf(player.getGamefield().getInsectCount()));

        updatedResources.add(fungiResources);
        updatedResources.add(plantResources);
        updatedResources.add(animalResources);
        updatedResources.add(insectResources);
        return updatedResources;
    }
}
