package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ServerMessageGenerator {
    public static JSONObject generateDrawnStarterCardMessage(StarterCard startercard){
        JSONObject message = new JSONObject();
        message.put("message","drawnStarterCards");
        message.put("starterCard",String.valueOf(startercard.getId()));
        return message;
    }
    public static JSONObject generateDrawnObjectiveCardsMessage (ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2){
        JSONObject message = new JSONObject();
        message.put("message","drawnObjectiveCards");
        message.put("objectiveCard1",String.valueOf(objectiveCard1.getId()));
        message.put("objectiveCard2",String.valueOf(objectiveCard2.getId()));
        return message;
    }

    public static JSONObject generateUpdateHandMessage (ArrayList<PlaceableCard> hand){
        JSONObject message = new JSONObject();
        JSONArray handArray = new JSONArray();
        message.put("message","updateHand");
        for(PlaceableCard card : hand){
            handArray.add(String.valueOf(card.getId()));
        }
        message.put("hand",handArray);
        return message;
    }
    public static JSONObject generateDrawableCardsMessage (ArrayList<PlaceableCard> resourceCards, ArrayList<PlaceableCard> goldCards){
        JSONObject message = new JSONObject();
        JSONArray resourceCardsArray = new JSONArray();
        JSONArray goldCardsArray = new JSONArray();
        message.put("message","drawableCards");
        for(PlaceableCard card : resourceCards){
            resourceCardsArray.add(String.valueOf(card.getId()));
        }
        for(PlaceableCard card : goldCards){
            goldCardsArray.add(String.valueOf(card.getId()));
        }
        message.put("resourceCards", resourceCardsArray);
        message.put("goldCards", goldCardsArray);
        return message;
    }

    public static JSONObject generateStartGameMessage () {
        JSONObject message = new JSONObject();
        message.put("message","start");
        return message;
    }

    public static JSONObject generateUpdatedScoreMessage (ArrayList<Integer> updatedScores) {
        JSONObject message = new JSONObject();
        JSONArray scoreArray = new JSONArray();
        message.put("message","updated score");
        scoreArray.addAll(updatedScores);
        message.put("score", scoreArray);
        return message;
    }

    public static JSONObject generateUpdatedResourcesMessage (int[] resources) {
        JSONObject message = new JSONObject();
        JSONArray updatedResources = new JSONArray();
        message.put("message", "updated resources");
        for(int i : resources) {
            updatedResources.add(i);
        }
        message.put("resources", updatedResources);
        return message;
    }
}
