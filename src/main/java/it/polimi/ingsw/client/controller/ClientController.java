package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.ObjectivesModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.ClientNetworkObserverInterface;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.application.Application;
import javafx.application.Platform;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ClientController implements ClientNetworkObserverInterface {

    private ClientConnectionManager clientConnectionManager;
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
        try {
            clientConnectionManager = new ClientConnectionManager(this,serverAddress,serverPort);
            instance = this;
        } catch (ServerUnreachableException e) {
            notifyConnectionLoss();
        }
        startGui();
    }

    @Override
    public void processMessage(JSONObject message) {
        clientMessageHandler.execute(message);
    }

    public void startGui() {
        // Launch the application
        Application.launch(ClientGUI.class);
    }

    @Override
    public void notifyConnectionLoss() {
        ClientStateModel.getIstance().setClientState(ClientState.LOST_CONNECTION_STATE);
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

    public void sendLeaveMessage(){
        clientConnectionManager.send(ClientMessageGenerator.generateLeaveMessage());
    }

    public void sendSetUpGameMessage(String gamename, int numOfPlayers) {
        clientConnectionManager.send(ClientMessageGenerator.generateSetUpGameMessage(gamename,numOfPlayers));

    }

    public void sendReadyMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateReadyMessage());
    }

    public void sendChosenStarterCardOrientation(int cardId, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenStarterCardOrientationMessage(cardId,facingUp));
    }
    
    public void sendChosenSecretObjectiveMessage(int cardId) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenSecretObjectiveMessage(cardId));
        ObjectivesModel.getIstance().setSecretObjectiveId(cardId);
    }
    
    public void sendPlaceMessage(int cardId, int x, int y, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generatePlaceMessage(cardId, x, y, facingUp));
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
        clientConnectionManager.shutdown();
        Platform.exit();
        System.exit(0);
    }
}
