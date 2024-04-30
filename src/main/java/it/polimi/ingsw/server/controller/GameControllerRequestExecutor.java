package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.ResponseGenerator;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import it.polimi.ingsw.util.customexceptions.FullHandException;
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
            default -> player.reply(ResponseGenerator.generateResponse("unexpectedCommand"));
        }
    }
    public static void ready(GameController game, ClientHandler player)
    {
        game.ready(player);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void chooseStarterCardOrientation(GameController game,JSONObject message, ClientHandler player)
    {
        int starterCardId =Integer.parseInt(message.get("starterCardId").toString());
        boolean facingUp= Boolean.parseBoolean(message.get("facingUp").toString());
        game.chooseStarterCardOrientations(player,starterCardId, facingUp);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void chooseSecretObjectiveCard(GameController game,JSONObject message, ClientHandler player)
    {
        int objectiveCardId =Integer.parseInt(message.get("objectiveCardId").toString());
        game.chooseSecretObjectiveCard(player, objectiveCardId);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void directDrawResourceCard(GameController game,ClientHandler player) throws EmptyDeckException, FullHandException {
        if (!player.CanDraw()) player.reply(ResponseGenerator.alreadyDrawnResponse());
        else {
            game.directDrawResourceCard(player);
            player.reply(ResponseGenerator.generateOkResponse());
        }
    }
    public static void directDrawGoldCard(GameController game,ClientHandler player) throws EmptyDeckException, FullHandException {
        game.directDrawGoldCard(player);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void drawLeftRevealedResourceCard(GameController game,ClientHandler player) throws FullHandException {
        game.drawLeftRevealedResourceCard(player);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void drawRightRevealedResourceCard(GameController game,ClientHandler player) throws FullHandException {
        game.drawRightRevealedResourceCard(player);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void drawLeftRevealedGoldCard(GameController game,ClientHandler player) throws FullHandException {
        game.drawLeftRevealedGoldCard(player);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void drawRightRevealedGoldCard(GameController game,ClientHandler player) throws FullHandException {
        game.drawRightRevealedGoldCard(player);
        player.reply(ResponseGenerator.generateOkResponse());
    }
    public static void place(GameController game, ClientHandler player, JSONObject message) throws CannotPlaceCardException {

        if(!player.isMyTurn()) player.reply(ResponseGenerator.generateNotYourTurnResponse());
        else if(!player.CanPlace()) player.reply(ResponseGenerator.alreadyPlacedResponse());
        else {
            try {
                int placeableCardId = Integer.parseInt(message.get("placeableCardId").toString());
                int x = Integer.parseInt(message.get("x").toString());
                int y = Integer.parseInt(message.get("y").toString());
                boolean facingUp = Boolean.parseBoolean(message.get("facingUp").toString());
                game.place(player, placeableCardId, facingUp, x, y);
                player.reply(ResponseGenerator.generateOkResponse());
            }
            catch (CannotPlaceCardException e)
            {
                player.reply(ResponseGenerator.notValidPlacementResponse());
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
        player.reply(ResponseGenerator.generateOkResponse());
    }




}
