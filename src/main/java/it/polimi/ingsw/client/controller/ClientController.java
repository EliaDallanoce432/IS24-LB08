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
     * Singleton pattern implementation to ensure only one instance of
     * `ClientController` exists throughout the application.
     * @return The singleton instance.
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
     * This method is called whenever a message is received from the
     * server. It delegates the message processing to the `ClientMessageHandler`.
     * @param message message to process.
     */
    @Override
    public void processMessage(JSONObject message) {
        clientMessageHandler.execute(message);
    }


    /**
     * Resets all the client models to their initial state.
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
     * Updates the client state to reflect the loss and displays a message. Called when a network connection loss is detected.
     */
    @Override
    public void notifyConnectionLoss() {
        ClientStateModel.getInstance().setClientState(ClientState.LOST_CONNECTION_STATE);
        clientConnectionManager.shutdown();
    }

    /**
     * Sends a message to the server to set the player's username.
     * @param username The username to be set for the player.
     */
    public void sendSetUsernameMessage(String username) {
        clientConnectionManager.send(ClientMessageGenerator.generateSetUsernameMessage(username));

    }

    /**
     * Sends a message to the server requesting a list of available games to join.
     */
    public void sendGetAvailableGamesMessage(){
        clientConnectionManager.send(ClientMessageGenerator.generateGetAvailableGamesMessage());

    }

    /**
     * Sends a message to the server requesting to join a specific game.
     * @param gameName The name of the game to join.
     */
    public void sendJoinGameMessage(String gameName){
        clientConnectionManager.send(ClientMessageGenerator.generateJoinGameMessage(gameName));

    }

    /**
     * Sends a message to the server indicating the client intends to leave the current game session.
     */
    public void sendLeaveMessage(){
        clientConnectionManager.send(ClientMessageGenerator.generateLeaveMessage());
    }

    /**
     * Sends a message to the server requesting to set up a new game.
     * @param gameName The desired name for the new game.
     * @param numOfPlayers The desired number of players for the game.
     */
    public void sendSetUpGameMessage(String gameName, int numOfPlayers) {
        clientConnectionManager.send(ClientMessageGenerator.generateSetUpGameMessage(gameName,numOfPlayers));

    }

    /**
     * Sends a message to the server indicating the client is ready to begin the game.
     */
    public void sendReadyMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateReadyMessage());
    }

    /**
     * Sends a message to the server informing the chosen side for a specific starter card identified by its ID.
     * @param cardId The unique identifier of the starter card.
     * @param facingUp Whether the chosen side of the card is facing up.
     */
    public void sendChosenStarterCardSideMessage(int cardId, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenStarterCardSideMessage(cardId,facingUp));
    }

    /**
     * Sends a message to the server indicating the chosen secret objective card identified by its ID.
     * @param cardId The unique identifier of the chosen secret objective card.
     */
    public void sendChosenSecretObjectiveMessage(int cardId) {
        clientConnectionManager.send(ClientMessageGenerator.generateChosenSecretObjectiveMessage(cardId));
        ObjectivesModel.getInstance().setSecretObjectiveId(cardId);
    }

    /**
     * Sends a message to the server requesting to place a card on the game board.
     * @param cardId The unique identifier of the card to be placed.
     * @param x The X-coordinate of the desired placement location.
     * @param y The Y-coordinate of the desired placement location.
     * @param facingUp Whether the card should be placed facing up or down.
     */
    public void sendPlaceMessage(int cardId, int x, int y, boolean facingUp) {
        clientConnectionManager.send(ClientMessageGenerator.generatePlaceMessage(cardId, x, y, facingUp));
    }

    /**
     * Sends a message to the server requesting to draw a resource card directly from the resource deck.
     */
    public void sendDirectDrawResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawResourceCardMessage());
    }

    /**
     * Sends a message to the server requesting to draw a resource card from the left revealed card of the resource deck.
     */
    public void sendDrawLeftResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftResourceCardMessage());
    }

    /**
     * Sends a message to the server requesting to draw a resource card from the right revealed card of the resource deck.
     */
    public void sendDrawRightResourceCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightResourceCardMessage());
    }
    /**
     * Sends a message to the server requesting to draw a gold card directly from the gold deck.
     */
    public void sendDirectDrawGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDirectDrawGoldCardMessage());
    }

    /**
     * Sends a message to the server requesting to draw a gold card from the left revealed card of the gold deck.
     */
    public void sendDrawLeftGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawLeftGoldCardMessage());
    }

    /**
     * Sends a message to the server requesting to draw a gold card from the right revealed card of the gold deck.
     */
    public void sendDrawRightGoldCardMessage() {
        clientConnectionManager.send(ClientMessageGenerator.generateDrawRightGoldCardMessage());
    }

    /**
     * Shuts down the client application gracefully when the client is running the GUI.
     */
    public void shutdownForGUI() {
        clientConnectionManager.shutdown();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Shuts down the client application gracefully when the client is running the CLI.
     */
    public void shutdownForCLI() {
        clientConnectionManager.shutdown();
        System.exit(0);
    }
}
