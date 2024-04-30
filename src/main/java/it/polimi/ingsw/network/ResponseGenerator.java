package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

public class ResponseGenerator {

    public static JSONObject generateResponse(String message) {
        JSONObject response = new JSONObject();
        response.put("response", message);
        return response;
    }
    public static JSONObject generateOkResponse () {
        JSONObject response = new JSONObject();
        response.put("response", "ok");
        return response;
    }

    public static JSONObject generateNotYourTurnResponse () {
        JSONObject response = new JSONObject();
        response.put("response", "Not your turn");
        return response;
    }

    public static JSONObject notValidPlacementResponse () {
        JSONObject response = new JSONObject();
        response.put("response", "Not valid placement");
        return response;
    }

    public static JSONObject alreadyPlacedResponse () {
        JSONObject response = new JSONObject();
        response.put("response", "Already placed");
        return response;
    }

    public static JSONObject emptyDeckResponse () {
        JSONObject response = new JSONObject();
        response.put("response", "Deck is empty");
        return response;
    }

    public static JSONObject alreadyDrawnResponse () {
        JSONObject response = new JSONObject();
        response.put("response", "Already drawn");
        return response;
    }

}
