package it.polimi.ingsw.util;

import org.json.simple.JSONObject;

public class ResponseGenerator {

    public static JSONObject response(String message) {
        JSONObject response = new JSONObject();
        response.put("response", message);
        return response;
    }
    public static JSONObject OKResponse() {
        JSONObject response = new JSONObject();
        response.put("response", "ok");
        return response;
    }

    public static JSONObject notYourTurnResponse() {
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

    public static JSONObject fullHandResponse() {
        JSONObject response = new JSONObject();
        response.put("response", "Full hand");
        return response;
    }

    public static JSONObject cantDrawYetResponse() {
        JSONObject response = new JSONObject();
        response.put("response", "cant draw yet");
        return response;
    }

}
