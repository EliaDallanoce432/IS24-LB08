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
        ClientHandler player = request.getClient();
        switch (message.get("command").toString()) {
            case "ready" -> ready(gameController, player);
            case "starterCard" -> chooseStarterCardOrientation(gameController, message, player);
            case "objectiveCard" -> chooseSecretObjectiveCard(gameController, message,player);
            case "directDrawResourceCard" -> directDrawResourceCard(gameController, player);
            case "directDrawGoldCard" -> directDrawGoldCard(gameController, player);
            case "drawLeftResourceCard" -> drawLeftRevealedResourceCard(gameController, player);
            case "drawRightResourceCard" -> drawRightRevealedResourceCard(gameController, player);
            case "drawLeftGoldCard" -> drawLeftRevealedGoldCard(gameController, player);
            case "drawRightGoldCard" -> drawRightRevealedGoldCard(gameController, player);
            case "place" -> place(gameController, player, message);
            case "leave" -> leave(gameController, player);
            default -> player.reply(ResponseGenerator.response("unexpectedCommand"));
        }
    }
    //TODO sostituire in tutti i metodi le reply
    public void ready(GameController game, ClientHandler player)
    {
        game.ready(player);
    }

    public void chooseStarterCardOrientation(GameController gameController,JSONObject message, ClientHandler player)
    {
        int starterCardId =Integer.parseInt(message.get("starterCardId").toString());
        boolean facingUp= Boolean.parseBoolean(message.get("facingUp").toString());
        gameController.chooseStarterCardOrientations(player,starterCardId, facingUp);
    }
    public void chooseSecretObjectiveCard(GameController gameController,JSONObject message, ClientHandler player)
    {
        int objectiveCardId =Integer.parseInt(message.get("objectiveCardId").toString());
        gameController.chooseSecretObjectiveCard(player, objectiveCardId);
    }
    public void directDrawResourceCard(GameController game,ClientHandler player) {
        if (!player.hasAlreadyPlaced())
            player.reply(ResponseGenerator.cantDrawYetResponse());
        else {
            try {
                game.directDrawResourceCard(player);
                player.reply(ResponseGenerator.OKResponse());
            } catch (EmptyDeckException e) {
                player.reply(ResponseGenerator.emptyDeckResponse());
            } catch (FullHandException e) {
                player.reply(ResponseGenerator.fullHandResponse());
            }
             catch (NotYourTurnException e) {
                player.reply(ResponseGenerator.notYourTurnResponse());
             } catch (CannotDrawException e) {
                player.reply(ResponseGenerator.cantDrawYetResponse());
            }
        }
    }
    public void directDrawGoldCard(GameController game,ClientHandler player) {
        if (!player.hasAlreadyPlaced())
            player.reply(ResponseGenerator.cantDrawYetResponse());
        else {
            try {
                game.directDrawGoldCard(player);
                player.reply(ResponseGenerator.OKResponse());
            }
            catch (EmptyDeckException e) {
                player.reply(ResponseGenerator.emptyDeckResponse());
            }
            catch (FullHandException e) {
                player.reply(ResponseGenerator.fullHandResponse());
            }
            catch (NotYourTurnException e) {
                player.reply(ResponseGenerator.notYourTurnResponse());
            }
            catch (CannotDrawException e) {
                player.reply(ResponseGenerator.cantDrawYetResponse());
            }
        }
    }
    public void drawLeftRevealedResourceCard(GameController game,ClientHandler player)  {
        if (!player.hasAlreadyPlaced())
            player.reply(ResponseGenerator.cantDrawYetResponse());
        else {
            try {
                game.drawLeftRevealedResourceCard(player);
                player.reply(ResponseGenerator.OKResponse());
            }
            catch (FullHandException e) {
                player.reply(ResponseGenerator.fullHandResponse());
            }
            catch (NotYourTurnException e) {
                player.reply(ResponseGenerator.notYourTurnResponse());
                }
            catch (CannotDrawException e) {
                player.reply(ResponseGenerator.cantDrawYetResponse());
            }
        }
    }
    public void drawRightRevealedResourceCard(GameController game,ClientHandler player) {
        if (!player.hasAlreadyPlaced())
            player.reply(ResponseGenerator.cantDrawYetResponse());
        else {
            try {
                game.drawRightRevealedResourceCard(player);
                player.reply(ResponseGenerator.OKResponse());
            } catch (FullHandException e) {
                player.reply(ResponseGenerator.fullHandResponse());
            } catch (NotYourTurnException e) {
                player.reply(ResponseGenerator.notYourTurnResponse());
            } catch (CannotDrawException e) {
                player.reply(ResponseGenerator.cantDrawYetResponse());
            }
        }
    }
    public void drawLeftRevealedGoldCard(GameController game,ClientHandler player) {
        if (!player.hasAlreadyPlaced())
            player.reply(ResponseGenerator.cantDrawYetResponse());
        else {
            try {
                game.drawLeftRevealedGoldCard(player);
                player.reply(ResponseGenerator.OKResponse());
            } catch (FullHandException e) {
                player.reply(ResponseGenerator.fullHandResponse());
            } catch (NotYourTurnException e) {
                player.reply((ResponseGenerator.notYourTurnResponse()));
            } catch (CannotDrawException e) {
                player.reply(ResponseGenerator.cantDrawYetResponse());
            }
        }
    }
    public void drawRightRevealedGoldCard(GameController game,ClientHandler player) {
        if (!player.hasAlreadyPlaced())
            player.reply(ResponseGenerator.cantDrawYetResponse());
        else {
            try {
                game.drawRightRevealedGoldCard(player);
                player.reply(ResponseGenerator.OKResponse());
            } catch (FullHandException e) {
                player.reply(ResponseGenerator.fullHandResponse());
            } catch (NotYourTurnException e) {
                player.reply(ResponseGenerator.notYourTurnResponse());
            } catch (CannotDrawException e) {
                player.reply(ResponseGenerator.cantDrawYetResponse());
            }
        }
    }
    public void place(GameController game, ClientHandler player, JSONObject message) {

        if(!player.hasAlreadyPlaced()) player.reply(ResponseGenerator.alreadyPlacedResponse());
        else {
            try {
                int placeableCardId = Integer.parseInt(message.get("placeableCardId").toString());
                int x = Integer.parseInt(message.get("x").toString());
                int y = Integer.parseInt(message.get("y").toString());
                boolean facingUp = Boolean.parseBoolean(message.get("facingUp").toString());
                game.place(player, placeableCardId, facingUp, x, y);
                player.reply(ResponseGenerator.OKResponse());
            }
            catch (CannotPlaceCardException e)
            {
                player.reply(ResponseGenerator.notValidPlacementResponse());
            }
            catch (NotYourTurnException e) {
                player.reply(ResponseGenerator.notYourTurnResponse());
            }
        }
    }
    public void leave(GameController game, ClientHandler player)
    {
        game.leaveGame(player);
        player.reply(ResponseGenerator.OKResponse());
    }

}
