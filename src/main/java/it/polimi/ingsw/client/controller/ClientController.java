package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.client.view.ViewController;
import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.ClientNetworkObserverInterface;
import it.polimi.ingsw.util.customexceptions.AlreadyPlacedInThisRoundException;
import it.polimi.ingsw.util.customexceptions.NotValidPlacement;
import it.polimi.ingsw.util.customexceptions.NotYourTurnException;
import javafx.application.Application;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ClientController implements Runnable, ClientNetworkObserverInterface {

    private final ClientConnectionManager clientConnectionManager;
    private final ClientModel clientModel;
    private ViewController viewController;
    private Boolean running;

    public ClientController (ClientConnectionManager clientConnectionManager)  {

        this.clientConnectionManager = clientConnectionManager;
        int randomNumber = (int) (Math.random() * 100);
        clientModel = new ClientModel("Guest-"+randomNumber);
        running = true;
    }


    @Override
    public void run() {
        startClient();
        while (running);
    }

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
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
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateSetUsernameMessage(username));
        if(response.get("response").equals("ok")) {
            System.out.println("Username set to " + username);
            clientModel.setUsername(username);
            return true;
        }
        else return false;

    }


    public String[] sendGetAvailableGamesMessage(){
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateGetAvailableGamesMessage());
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
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateSetUpGameMessage(gamename,numOfPlayers));
        if(response.get("response").equals("ok")) {
            return true;
        }
        else return false;
    }

    public void sendReadyMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateReadyMessage());
    }

    public void sendChosenStarterCardOrientation(int cardId, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenStarterCardOrientationMessage(cardId,facingUp));
    }
    
    public void sendChosenSecretObjectiveMessage(int cardId) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenSecretObjectiveMessage(cardId));
    }
    
    public boolean sendPlaceMessage(int cardId, int x, int y, boolean facingUp) throws NotYourTurnException, NotValidPlacement, AlreadyPlacedInThisRoundException {
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generatePlaceMessage(cardId,x,y,facingUp));
        if(response.get("response").equals("Not your turn")) {
            throw new NotYourTurnException();
        } else if (response.get("response").equals("Not valid placement")) {
            throw new NotValidPlacement();
        } else if (response.get("response").equals("Already placed")) {
            throw new AlreadyPlacedInThisRoundException();
        }
        return true;
    }

    public void sendDirectDrawResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawResourceCardMessage());
    }

    public void sendDrawLeftResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftResourceCardMessage());
    }

    public void sendDrawRightResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightResourceCardMessage());
    }

    public void sendDirectDrawGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawGoldCardMessage());
    }

    public void sendDrawLeftGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftGoldCardMessage());
    }

    public void sendDrawRightGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightGoldCardMessage());
    }

    public void shutdown() {
        //TODO gestire la chiusura
        running = false;
    }
}
