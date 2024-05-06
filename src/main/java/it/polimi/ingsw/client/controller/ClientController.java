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
    private boolean running;
    private static ClientController instance;
    private List<JSONObject> messages;

    public static ClientController getInstance(String serverAddress, int serverPort) {
        if (instance == null) instance = new ClientController(serverAddress, serverPort);
        return instance;
    }


    public static ClientController getInstance(){
        if (instance == null) instance = new ClientController("localhost", 12345); //should not happen !
        return instance;
    }

    public ClientController (String serverAddress, int serverPort)  {
        running = true;
        clientMessageHandler = new ClientMessageHandler();
        messages = Collections.synchronizedList(new ArrayList<>());
        try {
            this.clientConnectionManager = new ClientConnectionManager(this,serverAddress,serverPort);
            instance = this;
        } catch (ServerUnreachableException e) {
            //TODO gestire l'eccezione
            throw new RuntimeException(e);
        }
        startClient();
        startGui();
    }

    @Override
    public void addMessage(JSONObject message) {
        messages.addLast(message);
    }

    public void startClient() {
        while (running) {
            while (!messages.isEmpty()) {
                clientMessageHandler.execute(messages.getFirst());
                messages.removeFirst();
                System.out.println("executed request");
            }
        }
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
        clientMessageHandler.execute(clientConnectionManager.getReceivedMessage());
    }

    @Override
    public void notifyConnectionLoss() {
        //TODO in realtà deve mostrare la schermata di chisura dovuta al server che non risponde
        System.out.println("Connection lost");
        clientConnectionManager.shutdown();
    }

    public boolean sendSetUsernameMessage(String username) {
        JSONObject response = clientConnectionManager.send(ClientMessageGenerator.generateSetUsernameMessage(username), true);
        if(response.get("response").equals("ok")) {
            System.out.println("Username set to " + username);
            //playerModel.setUsername(username);
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
        GameFieldModel.getIstance().setStarterCard(cardId,facingUp);
    }
    
    public void sendChosenSecretObjectiveMessage(int cardId) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenSecretObjectiveMessage(cardId), true);
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
