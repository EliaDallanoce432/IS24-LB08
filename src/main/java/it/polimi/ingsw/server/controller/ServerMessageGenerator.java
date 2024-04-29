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
    public static JSONObject generateDrawableCardsMessage (ArrayList<Integer> resourceCards, ArrayList<Integer> goldCards){
        JSONObject message = new JSONObject();

        message.put("message","drawableCards");
        message.put("topDeckResourceCard", resourceCards.getFirst());
        message.put("leftResourceCard", resourceCards.get(1));
        message.put("rightResourceCard", resourceCards.getLast());
        message.put("topDeckGoldCard", goldCards.getFirst());
        message.put("leftGoldCard", goldCards.get(1));
        message.put("rightGoldCard", goldCards.getLast());
        return message;
    }

    public static JSONObject generateStartGameMessage () {
        JSONObject message = new JSONObject();
        message.put("message","start");
        return message;
    }

    public static JSONObject generateUpdatedScoreMessage (ArrayList<String> names,ArrayList<Integer> updatedScores) {
        JSONObject message = new JSONObject();
        JSONArray scoreArray = new JSONArray();
        message.put("message","updated score");
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
        message.put("message", "updated resources");
        for(int i : resources) {
            updatedResources.add(i);
        }
        message.put("resources", updatedResources);
        return message;
    }
}
