package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.ClientNetworkObserverInterface;
import javafx.application.Application;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Scanner;

public class ClientController implements Runnable, ClientNetworkObserverInterface {

    ClientConnectionManager clientConnectionManager;
    ClientModel clientModel;
    ClientView clientView;

    public ClientController (ClientConnectionManager clientConnectionManager)  {

        this.clientConnectionManager = clientConnectionManager;
        int randomNumber = (int) (Math.random() * 100);
        clientModel = new ClientModel("Guest-"+randomNumber);


    }


    @Override
    public void run() {
        startClient();

    }

    public void startClient() {
        // Create an instance of ClientView
        ClientView.clientController = this;

        // Launch the application
        Application.launch(ClientView.class);


    }


    @Override
    public void notifyIncomingMessage() {

    }

    @Override
    public void notifyConnectionLoss() {

    }

    public boolean sendSetUsernameMessage(String username) {
        JSONObject response = clientConnectionManager.send(MessageGenerator.generateSetUsernameMessage(username));
        if(response.get("response").equals("ok")) {
            System.out.println("Username set to " + username);
            clientModel.setUsername(username);
            return true;
        }
        else return false;

    }


    public String[] sendGetAvailableGamesMessage(){
        JSONObject response = clientConnectionManager.send(MessageGenerator.generateGetAvailableGamesMessage());
        JSONArray jsonArray = (JSONArray) response.get("games");

        if (!jsonArray.isEmpty()) {
            String[] games = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                games[i] = (String) jsonArray.get(i);
            }
            return games;
        }
        else return null;

    }

    public boolean sendSetUpGameMessage(String gamename, int numOfPlayers) {
        JSONObject response = clientConnectionManager.send(MessageGenerator.generateSetUpGameMessage(gamename,numOfPlayers));
        if(response.get("response").equals("ok")) {
            return true;
        }
        else return false;
    }
}
