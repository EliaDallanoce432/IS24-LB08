package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.util.ResponseGenerator;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

public class GameControllerRequestExecutor {
    GameController gameController;
    public GameControllerRequestExecutor(GameController gameController) {
        this.gameController = gameController;
    }

    public void execute (Request request)  {
        JSONObject message = request.getMessage();
        ClientHandler client = request.getClient();
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
            default -> client.send(ResponseGenerator.response("unexpectedCommand"));
        }
    }

    public void ready(ClientHandler player)
    {
        gameController.ready(player);
    }

    public void chooseStarterCardOrientation(JSONObject message, ClientHandler player) {
        int starterCardId =Integer.parseInt(message.get("starterCardId").toString());
        boolean facingUp= Boolean.parseBoolean(message.get("facingUp").toString());
        gameController.chooseStarterCardOrientations(player,starterCardId, facingUp);
    }

    public void chooseSecretObjectiveCard(JSONObject message, ClientHandler player) {
        int objectiveCardId =Integer.parseInt(message.get("objectiveCardId").toString());
        gameController.chooseSecretObjectiveCard(player, objectiveCardId);
    }

    public void directDrawResourceCard(ClientHandler client) {
        try {
            gameController.directDrawResourceCard(client);
            client.send(ServerMessageGenerator.updatedHandAndDecksMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(ServerMessageGenerator.turnPlayerUpdateMessage(gameController));
        } catch (EmptyDeckException | CannotDrawException | NotYourTurnException | FullHandException ignored) {}
    }

    public void directDrawGoldCard(ClientHandler client) {
        try {
            gameController.directDrawGoldCard(client);
            client.send(ServerMessageGenerator.updatedHandAndDecksMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(ServerMessageGenerator.turnPlayerUpdateMessage(gameController));
        }
        catch (EmptyDeckException | CannotDrawException | NotYourTurnException | FullHandException ignored) {}
    }

    public void drawLeftRevealedResourceCard(ClientHandler client)  {
        try {
            gameController.drawLeftRevealedResourceCard(client);
            client.send(ServerMessageGenerator.updatedHandAndDecksMessage(gameController.getCurrentPlayer(client)));
            gameController.broadcast(ServerMessageGenerator.turnPlayerUpdateMessage(gameController));
        }
        catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    public void drawRightRevealedResourceCard(ClientHandler player) {
        try {
            gameController.drawRightRevealedResourceCard(player);
            player.send(ServerMessageGenerator.updatedHandAndDecksMessage(gameController.getCurrentPlayer(player)));
            gameController.broadcast(ServerMessageGenerator.turnPlayerUpdateMessage(gameController));
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    public void drawLeftRevealedGoldCard(ClientHandler player) {
        try {
            gameController.drawLeftRevealedGoldCard(player);
            player.send(ServerMessageGenerator.updatedHandAndDecksMessage(gameController.getCurrentPlayer(player)));
            gameController.broadcast(ServerMessageGenerator.turnPlayerUpdateMessage(gameController));
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    public void drawRightRevealedGoldCard(ClientHandler player) {
        try {
            gameController.drawRightRevealedGoldCard(player);
            player.send(ServerMessageGenerator.updatedHandAndDecksMessage(gameController.getCurrentPlayer(player)));
            gameController.broadcast(ServerMessageGenerator.turnPlayerUpdateMessage(gameController));
        } catch (FullHandException | CannotDrawException | NotYourTurnException ignored) {}
    }

    public void place(ClientHandler player, JSONObject message) {
        try {
            int placeableCardId = Integer.parseInt(message.get("placeableCardId").toString());
            int x = Integer.parseInt(message.get("x").toString());
            int y = Integer.parseInt(message.get("y").toString());
            boolean facingUp = Boolean.parseBoolean(message.get("facingUp").toString());
            gameController.place(player, placeableCardId, facingUp, x, y);
            player.send(ServerMessageGenerator.successfulPlaceMessage(gameController.getCurrentPlayer(player)));
        }
        catch (CannotPlaceCardException e) {
            player.send(ServerMessageGenerator.cannotPlaceMessage(e.getMessage()));
        }
        catch (NotYourTurnException ignored) {
        }
    }

    public void leave(ClientHandler player) {
        gameController.leaveGame(player);
    }

}
