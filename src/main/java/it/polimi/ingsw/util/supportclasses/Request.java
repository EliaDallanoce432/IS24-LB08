package it.polimi.ingsw.util.supportclasses;

import it.polimi.ingsw.network.ClientHandler;
import org.json.simple.JSONObject;

public class Request {
    private ClientHandler client;
    private JSONObject message;

    public Request(ClientHandler client, JSONObject message) {
        this.client = client;
        this.message = message;
    }

    public JSONObject getMessage() {
        return message;
    }

    public ClientHandler getClient() {
        return client;
    }
}
