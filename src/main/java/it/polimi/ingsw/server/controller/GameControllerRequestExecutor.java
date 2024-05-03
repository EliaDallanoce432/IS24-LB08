package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.util.ResponseGenerator;
import it.polimi.ingsw.util.customexceptions.*;
import org.json.simple.JSONObject;

public class GameControllerRequestExecutor {

    public static void execute (GameController gamecontroller, JSONObject message, ClientHandler player) throws EmptyDeckException, FullHandException, CannotPlaceCardException {
        switch (message.get("command").toString()) {
            case "ready" -> ready(gamecontroller, player);
            case "starterCard" -> chooseStarterCardOrientation(gamecontroller, message, player);
            case "objectiveCard" -> chooseSecretObjectiveCard(gamecontroller, message,player);
            case "directDrawResourceCard" -> directDrawResourceCard(gamecontroller, player);
            case "directDrawGoldCard" -> directDrawGoldCard(gamecontroller, player);
            case "drawLeftResourceCard" -> drawLeftRevealedResourceCard(gamecontroller, player);
            case "drawRightResourceCard" -> drawRightRevealedResourceCard(gamecontroller, player);
            case "drawLeftGoldCard" -> drawLeftRevealedGoldCard(gamecontroller, player);
            case "drawRightGoldCard" -> drawRightRevealedGoldCard(gamecontroller, player);
            //case "placeStarterCard" -> placeStarterCard(gamecontroller,player, message);
            case "place" -> place(gamecontroller, player, message);
            case "leave" -> leave(gamecontroller, player);
            default -> player.reply(ResponseGenerator.response("unexpectedCommand"));
        }
    }

    public static void ready(GameController game, ClientHandler player)
    {
        game.ready(player);
        player.reply(ResponseGenerator.OKResponse());
    }
    public static void chooseStarterCardOrientation(GameController game,JSONObject message, ClientHandler player)
    {
        int starterCardId =Integer.parseInt(message.get("starterCardId").toString());
        boolean facingUp= Boolean.parseBoolean(message.get("facingUp").toString());
        game.chooseStarterCardOrientations(player,starterCardId, facingUp);
        player.reply(ResponseGenerator.OKResponse());
    }
    public static void chooseSecretObjectiveCard(GameController game,JSONObject message, ClientHandler player)
    {
        int objectiveCardId =Integer.parseInt(message.get("objectiveCardId").toString());
        game.chooseSecretObjectiveCard(player, objectiveCardId);
        player.reply(ResponseGenerator.OKResponse());
    }
    public static void directDrawResourceCard(GameController game,ClientHandler player) {
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
    public static void directDrawGoldCard(GameController game,ClientHandler player) {
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
    public static void drawLeftRevealedResourceCard(GameController game,ClientHandler player) throws FullHandException {
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
    public static void drawRightRevealedResourceCard(GameController game,ClientHandler player) throws FullHandException {
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
    public static void drawLeftRevealedGoldCard(GameController game,ClientHandler player) throws FullHandException {
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
    public static void drawRightRevealedGoldCard(GameController game,ClientHandler player) throws FullHandException {
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
    public static void place(GameController game, ClientHandler player, JSONObject message) {

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
    /* public static void placeStarterCard(GameController game, ClientHandler player, JSONObject message) {
            int starterCardId = Integer.parseInt(message.get("starterCardId").toString());
            boolean facingUp = Boolean.parseBoolean(message.get("facingUp").toString());
            game.place(player, starterCardId, facingUp);
            player.reply(ResponseGenerator.generateResponse("ok"));
    }*/
    public static void leave(GameController game, ClientHandler player)
    {
        game.leaveGame(player);
        player.reply(ResponseGenerator.OKResponse());
    }




}
