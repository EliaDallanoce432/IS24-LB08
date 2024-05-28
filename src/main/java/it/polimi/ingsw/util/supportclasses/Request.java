package it.polimi.ingsw.util.supportclasses;

import it.polimi.ingsw.network.ClientHandler;
import org.json.simple.JSONObject;

/**
 * This class represents a request from a client.
 * @param client The client who submitted the request.
 * @param message The message containing the request.
 */
public record Request(ClientHandler client, JSONObject message) {
}
