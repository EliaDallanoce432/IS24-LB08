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
    /*public static JSONObject generateUpdateHandMessage (ArrayList<PlaceableCard> hand){
        JSONObject message = new JSONObject();
        JSONArray handArray = new JSONArray();
        message.put("message","updateHand");
        for(PlaceableCard card : hand){
            handArray.add(String.valueOf(card.getId()));
        }
        message.put("hand",handArray);
        return message;
    }*/
//    public static JSONObject generateDrawableCardsMessage (ArrayList<Integer> resourceCards, ArrayList<Integer> goldCards){
//        JSONObject message = new JSONObject();
//
//        message.put("message","drawableCards");
//        message.put("topDeckResourceCard", resourceCards.getFirst());
//        message.put("leftResourceCard", resourceCards.get(1));
//        message.put("rightResourceCard", resourceCards.getLast());
//        message.put("topDeckGoldCard", goldCards.getFirst());
//        message.put("leftGoldCard", goldCards.get(1));
//        message.put("rightGoldCard", goldCards.getLast());
//        return message;
//    }

    public static JSONObject startGameMessage (Player player) {
        JSONObject message = new JSONObject();
        message.put("message","startGame");
        //invio la mano
        message.put("hand", updatedHand(player));
        //invio le drawable cards
        message.put("decks", updatedDecks());
        message.put("placementHistory", updatedPlacementHistory(player));
        message.put("secretObjectiveID", String.valueOf(player.getSecretObjective().getId()));
        message.put("token", player.getToken().toString());
        message.put("commonObjective1", String.valueOf(Game.getInstance().commonObjectives.getFirst().getId()));
        message.put("commonObjective2", String.valueOf(Game.getInstance().commonObjectives.getLast().getId()));
        return message;
    }

    public JSONObject updatedHandAndDecksMessage (Player player) {
        JSONObject message = new JSONObject();
        message.put("message","updatedHandAndDecks");
        message.put("updatedHand",updatedHand(player));
        message.put("updatedDecks",updatedDecks());
        return message;
    }

    public static JSONObject gameFieldUpdateMessage (Player player) {
        JSONObject message = new JSONObject();
        message.put("message","gameFieldUpdate");
        message.put("placementHistory", updatedPlacementHistory(player));
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

    private static JSONArray updatedPlacementHistory(Player player) {
        JSONArray placementHistory = new JSONArray();
        JSONObject card = new JSONObject();
        for(PlaceableCard placeableCard : player.getGamefield().getPlacementHistory()) {
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
        JSONObject card = new JSONObject();
        for(PlaceableCard cardInHand : player.getHand()){
            card.put("cardID", String.valueOf(cardInHand.getId()));
            handArray.add(card);
        }
        return handArray;
    }

    private static JSONArray updatedDecks() {
        Game game = Game.getInstance();
        JSONArray deckArray = new JSONArray();
        deckArray.add(new JSONObject().put("topDeckResourceCardID", String.valueOf(game.resourceCardDeck.getTopCardID())));
        deckArray.add(new JSONObject().put("leftRevealedResourceCardID", String.valueOf(game.resourceCardDeck.getLeftRevealedCardID())));
        deckArray.add(new JSONObject().put("rightRevealedResourceCardID", String.valueOf(game.resourceCardDeck.getRightRevealedCardID())));
        deckArray.add(new JSONObject().put("topDeckGoldCardID", String.valueOf(game.goldCardDeck.getTopCardID())));
        deckArray.add(new JSONObject().put("leftRevealedGoldCardID", String.valueOf(game.goldCardDeck.getLeftRevealedCardID())));
        deckArray.add(new JSONObject().put("rightRevealedGoldCardID", String.valueOf(game.goldCardDeck.getRightRevealedCardID())));
        return deckArray;
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
        message.put("score", scoreArray);
        return message;
    }

    /**
     * message sent when a client looses connection to inform the other clients that the game is getting cancelled
     * @return message
     */
    public static JSONObject closingGameMessage () {
        JSONObject message = new JSONObject();
        message.put("message","closingGame");
        return message;
    }
}
