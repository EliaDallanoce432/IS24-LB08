package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.ResponseGenerator;
import org.json.simple.JSONObject;

public class GameControllerRequestExecutor {

    public static void execute (GameController game, JSONObject message, ClientHandler player) {
        switch (message.get("command").toString()) {
            case "ready" -> ready(game, player);
            case "starterCard" -> chooseStarterCardOrientation(game, message, player);
            case "objectiveCard" -> ;
            case "directDrawResourceCard" -> ;
            case "directDrawGoldCard" -> ;
            case "drawLeftResourceCard" -> ;
            case "drawRightResourceCard" -> ;
            case "drawLeftGoldCard" -> ;
            case "drawRightGoldCard" -> ;
            case "place" -> ;
            case "leave" -> ;
            default -> player.reply(ResponseGenerator.generateResponse("unexpectedCommand"));
        }
    }
    public static void ready(GameController game, ClientHandler player)
    {
        game.ready(player);
        player.reply(ResponseGenerator.generateResponse("ok"));
    }
    public static void chooseStarterCardOrientation(GameController game,JSONObject message, ClientHandler player)
    {
        int starterCardId =Integer.parseInt(message.get("starterCardId").toString());
        boolean facingUp= Boolean.parseBoolean(message.get("facingUp").toString());
        game.chooseStarterCardOrientations(player,starterCardId, facingUp);
        player.reply(ResponseGenerator.generateResponse("ok"));
    }




}
