package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.GameState;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

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
     * this method is used to parse the commands received from clients and invokes the specific command they requested
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
        if(game.getGameState() == GameState.endGame) {
            if(!request.message().get("command").equals("leave")) {
                return;
            }
        }

        JSONObject message = request.message();
        System.out.println("executing message: " + message);
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
     * this method invokes the ready method in the game-controller
     * @param player who sent the request
     */
    private void ready(ClientHandler player)
    {
        gameController.ready(player);
    }

    private void chooseStarterCardOrientation(JSONObject message, ClientHandler player) {
        int starterCardId =Integer.parseInt(message.get("starterCardId").toString());
        boolean facingUp= Boolean.parseBoolean(message.get("facingUp").toString());
        gameController.chooseStarterCardSide(player,starterCardId, facingUp);
    }

    private void chooseSecretObjectiveCard(JSONObject message, ClientHandler player) {
        int objectiveCardId =Integer.parseInt(message.get("objectiveCardId").toString());
        gameController.chooseSecretObjectiveCard(player, objectiveCardId);
    }

    private void directDrawResourceCard(ClientHandler client) {
        try {
            gameController.directDrawResourceCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (EmptyDeckException | CannotDrawException | NotYourTurnException | FullHandException ignored) {}
    }

    private void directDrawGoldCard(ClientHandler client) {
        try {
            gameController.directDrawGoldCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        }
        catch (EmptyDeckException | CannotDrawException | NotYourTurnException | FullHandException ignored) {}
    }

    private void drawLeftRevealedResourceCard(ClientHandler client)  {
        try {
            gameController.drawLeftRevealedResourceCard(client);
            client.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        }
        catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    private void drawRightRevealedResourceCard(ClientHandler player) {
        try {
            gameController.drawRightRevealedResourceCard(player);
            player.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(player)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    private void drawLeftRevealedGoldCard(ClientHandler player) {
        try {
            gameController.drawLeftRevealedGoldCard(player);
            player.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(player)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    private void drawRightRevealedGoldCard(ClientHandler player) {
        try {
            gameController.drawRightRevealedGoldCard(player);
            player.send(messageGenerator.updatedHandMessage(gameController.getCurrentPlayer(player)));
            gameController.broadcast(messageGenerator.updatedDecksMessage());
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    private void place(ClientHandler player, JSONObject message) {
        try {
            int placeableCardId = Integer.parseInt(message.get("placeableCardId").toString());
            int x = Integer.parseInt(message.get("x").toString());
            int y = Integer.parseInt(message.get("y").toString());
            boolean facingUp = Boolean.parseBoolean(message.get("facingUp").toString());
            gameController.place(player, placeableCardId, facingUp, x, y);
            player.send(messageGenerator.successfulPlaceMessage(gameController.getCurrentPlayer(player)));
        }
        catch (CannotPlaceCardException e) {
            player.send(messageGenerator.cannotPlaceMessage(e.getMessage()));
        }
    }

    private void leave(ClientHandler player) {
        gameController.leaveGame(player);
    }

}
