package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.model.ObjectivesModel;
import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.ClientNetworkObserverInterface;
import it.polimi.ingsw.util.customexceptions.AlreadyPlacedInThisRoundException;
import it.polimi.ingsw.util.customexceptions.NotValidPlacement;
import it.polimi.ingsw.util.customexceptions.NotYourTurnException;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import javafx.application.Application;
import javafx.application.Platform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientController implements ClientNetworkObserverInterface {

    private final ClientConnectionManager clientConnectionManager;
    private final ClientMessageHandler clientMessageHandler;
    private static ClientController instance;

    public static ClientController getInstance(String serverAddress, int serverPort) {
        if (instance == null) instance = new ClientController(serverAddress, serverPort);
        return instance;
    }


    public static ClientController getInstance(){
        if (instance == null) instance = new ClientController("localhost", 12345); //should not happen !
        return instance;
    }

    public ClientController (String serverAddress, int serverPort)  {
        clientMessageHandler = new ClientMessageHandler();
        //messages = Collections.synchronizedList(new ArrayList<>());
        try {
            this.clientConnectionManager = new ClientConnectionManager(this,serverAddress,serverPort);
            instance = this;
        } catch (ServerUnreachableException e) {
            //TODO gestire l'eccezione
            throw new RuntimeException(e);
        }
        startGui();
    }

    @Override
    public void processMessage(JSONObject message) {
        clientMessageHandler.execute(message);
    }

    public ClientConnectionManager getClientConnectionManager() {
        return clientConnectionManager;
    }

    public void startGui() {

        // Launch the application
        Application.launch(ClientGUI.class);
    }

    @Override
    public void notifyConnectionLoss() {
        //TODO in realtà deve mostrare la schermata di chisura dovuta al server che non risponde
        System.out.println("Connection lost");
        clientConnectionManager.shutdown();
    }

    public void sendSetUsernameMessage(String username) {
        clientConnectionManager.send(ClientMessageGenerator.generateSetUsernameMessage(username));

    }

    public void sendGetAvailableGamesMessage(){
        clientConnectionManager.send(ClientMessageGenerator.generateGetAvailableGamesMessage());

    }

    public void sendJoinGameMessage(String gameName){
        clientConnectionManager.send(ClientMessageGenerator.generateJoinGameMessage(gameName));

    }

    public void sendSetUpGameMessage(String gamename, int numOfPlayers) {
        clientConnectionManager.send(ClientMessageGenerator.generateSetUpGameMessage(gamename,numOfPlayers));

    }

    public void sendReadyMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateReadyMessage());
    }

    public void sendChosenStarterCardOrientation(int cardId, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenStarterCardOrientationMessage(cardId,facingUp));
        GameFieldModel.getIstance().setStarterCard(cardId,facingUp);
    }
    
    public void sendChosenSecretObjectiveMessage(int cardId) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenSecretObjectiveMessage(cardId));
        ObjectivesModel.getIstance().setSecretObjectiveId(cardId);
    }
    
    public boolean sendPlaceMessage(int cardId, int x, int y, boolean facingUp) throws NotYourTurnException, NotValidPlacement, AlreadyPlacedInThisRoundException {
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generatePlaceMessage(cardId,x,y,facingUp), true);
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
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawResourceCardMessage(), true);
    }

    public void sendDrawLeftResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftResourceCardMessage(), true);
    }

    public void sendDrawRightResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightResourceCardMessage(), true);
    }

    public void sendDirectDrawGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawGoldCardMessage(), true);
    }

    public void sendDrawLeftGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftGoldCardMessage(), true);
    }

    public void sendDrawRightGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightGoldCardMessage(), true);
    }

    public void shutdown() {
        clientConnectionManager.shutdown();
        Platform.exit();
        System.exit(0);
    }
}
