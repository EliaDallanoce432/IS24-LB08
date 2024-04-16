package it.polimi.ingsw.network;

import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;
import org.json.simple.JSONObject;

public class LobbyRequestExecutor {

    public static void execute(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        switch (message.get("command").toString()) {
            case "setusername" -> setUsername(lobby, message, clientHandler);
        }
    }

    private static void setUsername(Lobby lobby, JSONObject message, ClientHandler clientHandler) {
        try {
            lobby.setUsername(message.get("parameters").toString(),clientHandler);
        } catch (AlreadyTakenUsernameException e) {
            //TODO rispondere che il nome è già preso
        }
        //TODO rispondere che è andato tutto bene
    }

}
