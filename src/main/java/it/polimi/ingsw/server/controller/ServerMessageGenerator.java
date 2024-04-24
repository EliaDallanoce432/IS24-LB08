package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import org.json.simple.JSONObject;

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
}
