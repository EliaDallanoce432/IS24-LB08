package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.client.view.ViewController;
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

public class ClientController implements ClientNetworkObserverInterface {

    private final ClientConnectionManager clientConnectionManager;
    private final ClientModel clientModel;
    private ViewController viewController;

    public ClientController (String serverAddress, int serverPort)  {
        try {
            this.clientConnectionManager = new ClientConnectionManager(this,serverAddress,serverPort);
        } catch (ServerUnreachableException e) {
            //TODO gestire l'eccezione
            throw new RuntimeException(e);
        }
        int randomNumber = (int) (Math.random() * 100);
        clientModel = new ClientModel("Guest-"+randomNumber);
        clientConnectionManager.send(ClientMessageGenerator.generateSetUsernameMessage(clientModel.getUsername()),true);
        startGui();
    }

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }

    public ViewController getViewController() {
        return viewController;
    }

    public ClientConnectionManager getClientConnectionManager() {
        return clientConnectionManager;
    }

    public void startGui() {
        // Create an instance of ClientView
        ClientGUI.clientController = this;
        // Launch the application
        Application.launch(ClientGUI.class);
    }


    @Override
    public void notifyIncomingMessage() {
        ClientControllerRequestExecutor.execute(this,clientConnectionManager.getReceivedMessage(),clientConnectionManager);
    }

    @Override
    public void notifyConnectionLoss() {
        //TODO in realtà deve mostrare la schermata di chisura dovuta al server che non risponde
        System.out.println("Connection lost");
        clientConnectionManager.shutdown();
    }

    public void loadStarterCards(int starterCardId) {
        viewController.loadStarterCard(starterCardId);
    }

    public void loadObjectiveCards(int id1, int id2){
        viewController.loadObjectiveCards(id1,id2);
    }

    public void loadGameBoard(){
        viewController.loadGameBoard(clientModel.getStarterCardId(),100,101,clientModel.getSecretObjectiveId());
    }

    public boolean sendSetUsernameMessage(String username) {
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateSetUsernameMessage(username), true);
        if(response.get("response").equals("ok")) {
            System.out.println("Username set to " + username);
            clientModel.setUsername(username);
            return true;
        }
        else return false;

    }


    public ArrayList<String> sendGetAvailableGamesMessage(){
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateGetAvailableGamesMessage(), true);
        JSONArray jsonArray = (JSONArray) response.get("games");
        ArrayList<String> games = new ArrayList<>();
        if (!jsonArray.isEmpty()) {
            for (Object o : jsonArray) {
                games.add((String) o);
            }
        }
        return games;
    }

    public boolean sendJoinGameMessage(String gameName){
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateJoinGameMessage(gameName), true);
        if(response.get("response").equals("ok")) {
            return true;
        }
        else return false;
    }

    public boolean sendSetUpGameMessage(String gamename, int numOfPlayers) {
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateSetUpGameMessage(gamename,numOfPlayers), true);
        if(response.get("response").equals("ok")) {
            return true;
        }
        else return false;
    }

    public boolean sendReadyMessage() {
        System.out.println("sending message...");
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateReadyMessage(), true);
        return response.get("response").equals("ok");
    }

    public void sendChosenStarterCardOrientation(int cardId, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenStarterCardOrientationMessage(cardId,facingUp), true);
        clientModel.setStarterCardId(cardId);
        clientModel.setStarterCardFacingUp(facingUp);
    }
    
    public void sendChosenSecretObjectiveMessage(int cardId) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenSecretObjectiveMessage(cardId), true);
        clientModel.setSecretObjectiveId(cardId);
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
