package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.util.ResponseGenerator;
import org.json.simple.JSONObject;

public class ClientControllerRequestExecutor {

    public static void execute (ClientController clientController, JSONObject message, ClientConnectionManager clientConnectionManager) {
        switch (message.get("message").toString()) {
            case "drawnStarterCards" -> loadStarterCards(clientController,message,clientConnectionManager);
            case "drawnObjectiveCards" -> loadObjectiveCards(clientController,message,clientConnectionManager);
            case "start" ->  loadGameBoard(clientController,clientConnectionManager);
            case "updateHand" -> updateCardsInHand(clientController,message,clientConnectionManager);
            default -> clientConnectionManager.reply(ResponseGenerator.response("Invalid command"));
        }
    }

    private static void loadStarterCards (ClientController clientController, JSONObject message, ClientConnectionManager clientConnectionManager) {
        clientController.loadStarterCards(Integer.parseInt(message.get("starterCard").toString()));
        clientConnectionManager.reply(ResponseGenerator.OKResponse());
    }

    private static void loadObjectiveCards (ClientController clientController, JSONObject message, ClientConnectionManager clientConnectionManager) {
        clientController.loadObjectiveCards(Integer.parseInt(message.get("objectiveCard1").toString()), Integer.parseInt(message.get("objectiveCard2").toString()));
        clientConnectionManager.reply(ResponseGenerator.OKResponse());
    }

    private static void loadGameBoard(ClientController clientController, ClientConnectionManager clientConnectionManager){
        clientController.loadGameBoard();
        clientConnectionManager.reply(ResponseGenerator.OKResponse());
    }

    private static void updateCardsInHand(ClientController clientController, JSONObject message, ClientConnectionManager clientConnectionManager){


    }
}
