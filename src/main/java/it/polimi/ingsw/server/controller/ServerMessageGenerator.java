package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.model.card.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
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

    public static JSONObject startGameMessage (ArrayList<PlaceableCard> hand, ArrayList<Card> resourceCards, ArrayList<Card> goldCards, StarterCard s, ObjectiveCard o, ArrayList<ObjectiveCard> commonObjectives) {
        JSONObject message = new JSONObject();
        message.put("message","startGame");
        //invio la mano
        JSONArray handArray = new JSONArray();
        for(PlaceableCard card : hand){
            handArray.add(String.valueOf(card.getId()));
        }
        message.put("hand",handArray);
        //invio le drawable cards
        message.put("topDeckResourceCardID", String.valueOf(resourceCards.getFirst().getId()));
        message.put("leftResourceCardID", String.valueOf(resourceCards.get(1).getId()));
        message.put("rightResourceCardID", String.valueOf(resourceCards.getLast().getId()));
        message.put("topDeckGoldCardID", String.valueOf(goldCards.getFirst().getId()));
        message.put("leftGoldCardID", String.valueOf(goldCards.get(1).getId()));
        message.put("rightGoldCardID", String.valueOf(goldCards.getLast().getId()));

        message.put("starterCardID", String.valueOf(s.getId()));
        message.put("starterCardOrientation", String.valueOf(s.isFacingUp()));
        message.put("secretObjectiveID", String.valueOf(o.getId()));

        JSONArray commonObj = new JSONArray();
        for(ObjectiveCard card : commonObjectives){
            JSONObject obj = new JSONObject();
            obj.put("objectiveID", String.valueOf(card.getId()));
            commonObj.add(obj);
        }
        message.put("commonObjectives",commonObj);
        return message;
    }

    public JSONObject updatedHandAndDecksMessage (ArrayList<PlaceableCard> hand, ArrayList<Integer> resourceCards, ArrayList<Integer> goldCards) {
        JSONObject message = new JSONObject();
        message.put("message","updatedHandAndDecks");
        //invio la mano
        JSONArray handArray = new JSONArray();
        for(PlaceableCard card : hand){
            handArray.add(String.valueOf(card.getId()));
        }
        message.put("hand",handArray);
        //invio le drawable cards
        message.put("topDeckResourceCard", resourceCards.getFirst());
        message.put("leftResourceCard", resourceCards.get(1));
        message.put("rightResourceCard", resourceCards.getLast());
        message.put("topDeckGoldCard", goldCards.getFirst());
        message.put("leftGoldCard", goldCards.get(1));
        message.put("rightGoldCard", goldCards.getLast());

        return message;
    }

    public static JSONObject generateUpdatedScoreMessage (ArrayList<String> names,ArrayList<Integer> updatedScores) {
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

    public static JSONObject generateUpdatedResourcesMessage (int[] resources) {
        JSONObject message = new JSONObject();
        JSONArray updatedResources = new JSONArray();
        message.put("message", "updatedResources");
        for(int i : resources) {
            updatedResources.add(i);
        }
        message.put("resources", updatedResources);
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
