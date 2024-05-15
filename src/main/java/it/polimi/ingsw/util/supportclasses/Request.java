package it.polimi.ingsw.util.supportclasses;

import it.polimi.ingsw.network.ClientHandler;
import org.json.simple.JSONObject;

public record Request(ClientHandler client, JSONObject message) {
}
