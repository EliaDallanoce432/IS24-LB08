package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.ClientNetworkObserver;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.application.Platform;
import org.json.simple.JSONObject;

/**
 * This class acts as the central controller for the client-side application of the Codex game.
 */
public class ClientController implements ClientNetworkObserver {

    private final ClientConnectionManager clientConnectionManager;
    private final ClientMessageHandler clientMessageHandler;
    private static ClientController instance;

    public static ClientController getInstance(String serverAddress, int serverPort) throws ServerUnreachableException {
        if (instance == null) instance = new ClientController(serverAddress, serverPort);
        return instance;
    }

    /**
     * singleton pattern implementation to ensure only one instance of
     * `ClientController` exists throughout the application
     * @return The singleton instance
     */
    public static ClientController getInstance() {
        if (instance == null) {
            try {
                instance = new ClientController("localhost", 12345); //should not happen !
            } catch (ServerUnreachableException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public ClientController (String serverAddress, int serverPort) throws ServerUnreachableException {
        clientMessageHandler = new ClientMessageHandler();

        clientConnectionManager = new ClientConnectionManager(this,serverAddress,serverPort);
        instance = this;
    }

    /**
     * invokes `processMessage` method. This method is called whenever a message is received from the
     * server. It delegates the message processing to the `ClientMessageHandler`.
     * @param message message to process
     */
    @Override
    public void processMessage(JSONObject message) {
        clientMessageHandler.execute(message);
    }


    /**
     * resets all the client models to their initial state.
     */
    public void resetModels(){
        DeckModel.getInstance().clear();
        ObjectivesModel.getInstance().clear();
        GameFieldModel.getInstance().clear();
        HandModel.getInstance().clear();
        PlayerModel.getInstance().clear();
        ScoreBoardModel.getInstance().clear();
        SelectableCardsModel.getInstance().clear();
    }


    /**
     * updates the client state to reflect the loss and displays a message. Called when a network connection loss is detected
     */
    @Override
    public void notifyConnectionLoss() {
        ClientStateModel.getInstance().setClientState(ClientState.LOST_CONNECTION_STATE);
        System.out.println("Connection lost");
        clientConnectionManager.shutdown();
    }

    /**
     * sends a message to the server to set the player's username.
     * @param username The username to be set for the player
     */
    public void sendSetUsernameMessage(String username) {
        clientConnectionManager.send(ClientMessageGenerator.generateSetUsernameMessage(username));

    }

    /**
     * sends a message to the server requesting a list of available games to join
     */
    public void sendGetAvailableGamesMessage(){
        clientConnectionManager.send(ClientMessageGenerator.generateGetAvailableGamesMessage());

    }

    /**
     * sends a message to the server requesting to join a specific game
     * @param gameName The name of the game to join
     */
    public void sendJoinGameMessage(String gameName){
        clientConnectionManager.send(ClientMessageGenerator.generateJoinGameMessage(gameName));

    }

    /**
     * sends a message to the server indicating the client intends to leave the current game session
     */
    public void sendLeaveMessage(){
        clientConnectionManager.send(ClientMessageGenerator.generateLeaveMessage());
    }

    /**
     * sends a message to the server requesting to set up a new game.
     * @param gameName The desired name for the new game.
     * @param numOfPlayers The desired number of players for the game.
     */
    public void sendSetUpGameMessage(String gameName, int numOfPlayers) {
        clientConnectionManager.send(ClientMessageGenerator.generateSetUpGameMessage(gameName,numOfPlayers));

    }

    /**
     * sends a message to the server indicating the client is ready to begin the game
     */
    public void sendReadyMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateReadyMessage());
    }

    /**
     * sends a message to the server informing the chosen side for a specific starter card identified by its ID.
     * @param cardId The unique identifier of the starter card.
     * @param facingUp Whether the chosen side of the card is facing up.
     */
    public void sendChosenStarterCardSideMessage(int cardId, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenStarterCardSideMessage(cardId,facingUp));
    }

    /**
     * sends a message to the server indicating the chosen secret objective card identified by its ID
     * @param cardId The unique identifier of the chosen secret objective card.
     */
    public void sendChosenSecretObjectiveMessage(int cardId) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenSecretObjectiveMessage(cardId));
        ObjectivesModel.getInstance().setSecretObjectiveId(cardId);
    }

    /**
     * sends a message to the server requesting to place a card on the game board
     * @param cardId The unique identifier of the card to be placed.
     * @param x The X-coordinate of the desired placement location.
     * @param y The Y-coordinate of the desired placement location.
     * @param facingUp Whether the card should be placed facing up or down.
     */
    public void sendPlaceMessage(int cardId, int x, int y, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generatePlaceMessage(cardId, x, y, facingUp));
    }

    /**
     * sends a message to the server requesting to draw a resource card directly from the resource deck.
     */
    public void sendDirectDrawResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawResourceCardMessage());
    }

    /**
     * sends a message to the server requesting to draw a resource card from the left side of the resource deck.
     */
    public void sendDrawLeftResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftResourceCardMessage());
    }

    /**
     * sends a message to the server requesting to draw a resource card from the right side of the resource deck.
     */
    public void sendDrawRightResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightResourceCardMessage());
    }
    /**
     * sends a message to the server requesting to draw a gold card directly from the gold deck.
     */
    public void sendDirectDrawGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawGoldCardMessage());
    }

    /**
     * sends a message to the server requesting to draw a gold card from the left side of the gold deck.
     */
    public void sendDrawLeftGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftGoldCardMessage());
    }

    /**
     * sends a message to the server requesting to draw a gold card from the right side of the gold deck.
     */
    public void sendDrawRightGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightGoldCardMessage());
    }

    /**
     * shuts down the client application gracefully
     */
    public void shutdownForGUI() {
        clientConnectionManager.shutdown();
        Platform.exit();
        System.exit(0);
    }

    public void shutdownForCLI() {
        clientConnectionManager.shutdown();
        System.exit(0);
    }
}
