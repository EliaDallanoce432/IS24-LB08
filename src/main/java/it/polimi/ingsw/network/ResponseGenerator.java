package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

public class ResponseGenerator {

    public static JSONObject generateResponse(String message) {
        JSONObject response = new JSONObject();
        response.put("response", message);
        return response;
    }
}
