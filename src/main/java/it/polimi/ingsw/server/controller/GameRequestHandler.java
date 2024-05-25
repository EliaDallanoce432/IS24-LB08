package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.GameState;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

/**
 * This class handles incoming requests from clients related to the game and delegates them to the appropriate methods in the game controller.
 */
public class GameRequestHandler {
    private final GameController gameController;
    private final ServerMessageGenerator messageGenerator;
    private final Game game;
    public GameRequestHandler(GameController gameController, ServerMessageGenerator messageGenerator, Game game) {
        this.messageGenerator = messageGenerator;
        this.gameController = gameController;
        this.game = game;
    }

    /**
     * parses the commands received from clients and invokes the specific method in the game controller based on the requested action
     * @param request that is about to be executed
     */
    public void execute (Request request)  {
        if(game.getGameState() == GameState.lastRound) {
            if(request.message().get("command").equals("directDrawResourceCard") ||
                request.message().get("command").equals("directDrawGoldCard") ||
                request.message().get("command").equals("drawLeftResourceCard") ||
                request.message().get("command").equals("drawRightResourceCard") ||
                request.message().get("command").equals("drawLeftGoldCard") ||
                request.message().get("command").equals("drawRightGoldCard")) {
                return;
            }
        }
        if(game.getGameState() == GameState.endGame || game.getGameState() == GameState.aClientDisconnected) {
            if(!request.message().get("command").equals("leave")) {
                return;
            }
        }
        JSONObject message = request.message();
        ClientHandler client = request.client();
        switch (message.get("command").toString()) {
            case "ready" -> ready(client);
            case "starterCard" -> chooseStarterCardOrientation(message, client);
            case "objectiveCard" -> chooseSecretObjectiveCard(message,client);
            case "directDrawResourceCard" -> directDrawResourceCard(client);
            case "directDrawGoldCard" -> directDrawGoldCard(client);
            case "drawLeftResourceCard" -> drawLeftRevealedResourceCard(client);
            case "drawRightResourceCard" -> drawRightRevealedResourceCard(client);
            case "drawLeftGoldCard" -> drawLeftRevealedGoldCard(client);
            case "drawRightGoldCard" -> drawRightRevealedGoldCard(client);
            case "place" -> place(client, message);
            case "leave" -> leave(client);
            default -> { /*do nothing */}
        }
    }

    /**
     * invokes the ready method in the game controller
     * @param player who sent the request
     */
    private void ready(ClientHandler player)
    {
        gameController.ready(player);
    }

    /**
     * processes a request from a client to choose the orientation of their starter card.
     * @param message JSON message containing the starter card ID and facing up information
     * @param client client handler representing the player who sent the request
     */
    private void chooseStarterCardOrientation(JSONObject message, ClientHandler client) {
        int starterCardId =Integer.parseInt(message.get("starterCardId").toString());
        boolean facingUp= Boolean.parseBoolean(message.get("facingUp").toString());
        gameController.chooseStarterCardSide(client,starterCardId, facingUp);
    }

    /**
     * processes a request from a client to choose their secret objective card
     * @param message JSON message containing the objective card ID and facing up information
     * @param client client handler representing the player who sent the request
     */
    private void chooseSecretObjectiveCard(JSONObject message, ClientHandler client) {
        int objectiveCardId =Integer.parseInt(message.get("objectiveCardId").toString());
        gameController.chooseSecretObjectiveCard(client, objectiveCardId);
    }

    /**
     * processes a request from a client to directly draw a resource card from the deck
     * @param client client handler representing the player who sent the request.
     */
    private void directDrawResourceCard(ClientHandler client) {
        try {
            gameController.directDrawResourceCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (EmptyDeckException | CannotDrawException | NotYourTurnException | FullHandException ignored) {}
    }

    /**
     * processes a request from a client to directly draw a gold card from the deck
     * @param client client handler representing the player who sent the request.
     */
    private void directDrawGoldCard(ClientHandler client) {
        try {
            gameController.directDrawGoldCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        }
        catch (EmptyDeckException | CannotDrawException | NotYourTurnException | FullHandException ignored) {}
    }

    /**
     * processes a request from a client to draw the revealed resource card from the left side of the deck
     * @param client client handler representing the player who sent the request
     */
    private void drawLeftRevealedResourceCard(ClientHandler client)  {
        try {
            gameController.drawLeftRevealedResourceCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        }
        catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    /**
     * processes a request from a client to draw the revealed resource card from the right side of the deck
     * @param client client handler representing the player who sent the request
     */
    private void drawRightRevealedResourceCard(ClientHandler client) {
        try {
            gameController.drawRightRevealedResourceCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    /**
     * processes a request from a client to draw the revealed gold card from the left side of the deck
     * @param client client handler representing the player who sent the request
     */
    private void drawLeftRevealedGoldCard(ClientHandler client) {
        try {
            gameController.drawLeftRevealedGoldCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    /**
     * processes a request from a client to draw the revealed gold card from the right side of the deck
     * @param client client handler representing the player who sent the request
     */
    private void drawRightRevealedGoldCard(ClientHandler client) {
        try {
            gameController.drawRightRevealedGoldCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    /**
     * processes a request from a client to place a card on the board
     * @param client client handler representing the player who sent the request
     * @param message JSON message containing information about the card to be placed
     */
    private void place(ClientHandler client, JSONObject message) {
        try {
            int placeableCardId = Integer.parseInt(message.get("placeableCardId").toString());
            int x = Integer.parseInt(message.get("x").toString());
            int y = Integer.parseInt(message.get("y").toString());
            boolean facingUp = Boolean.parseBoolean(message.get("facingUp").toString());
            gameController.place(client, placeableCardId, facingUp, x, y);
            client.send(messageGenerator.successfulPlaceMessage(gameController.getCurrentPlayer(client)));
        }
        catch (CannotPlaceCardException e) {
            client.send(messageGenerator.cannotPlaceMessage(e.getMessage()));
        }
    }

    /**
     * processes a request from a client to leave the game
     * @param player client handler representing the player who sent the request
     */
    private void leave(ClientHandler player) {
        gameController.leaveGame(player);
    }

}
