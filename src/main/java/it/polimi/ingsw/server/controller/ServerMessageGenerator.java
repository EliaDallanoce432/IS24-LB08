package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ServerMessageGenerator {

    private Game game;

    public ServerMessageGenerator(Game game) {
        this.game = game;
    }

    public JSONObject cardsSelectionMessage (StarterCard startercard, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        JSONObject message = new JSONObject();
        message.put("message", "cardsSelection");
        message.put("starterCardID", String.valueOf(startercard.getId()));
        message.put("objectiveCardID1", String.valueOf(objectiveCard1.getId()));
        message.put("objectiveCardID2", String.valueOf(objectiveCard2.getId()));
        return message;
    }

    public JSONObject startGameMessage (GameController gameController, Player player) {
        JSONObject message = new JSONObject();
        message.put("message","startGame");
        message.put("hand", updatedHand(player));
        message.put("decks", updatedDecks());
        message.put("placementHistory", updatedPlacementHistory(player));
        message.put("resources", updatedResources(player));
        message.put("secretObjectiveID", String.valueOf(player.getSecretObjective().getId()));
        message.put("token", player.getToken().toString());
        message.put("commonObjective1", String.valueOf(game.commonObjectives.getFirst().getId()));
        message.put("commonObjective2", String.valueOf(game.commonObjectives.getLast().getId()));
        message.put("firstPlayer", gameController.getTurnPlayerUsername());
        return message;
    }

    public JSONObject turnPlayerUpdateMessage(GameController gameController) {
        JSONObject message = new JSONObject();
        message.put("message","turnPlayerUpdate");
        message.put("player", gameController.getTurnPlayerUsername());
        return message;
    }

    public JSONObject updatedHandMessage(Player player) {
        JSONObject message = new JSONObject();
        message.put("message","updatedHand");
        message.put("updatedHand",updatedHand(player));
        return message;
    }

    public JSONObject updatedDecksMessage() {
        JSONObject message = new JSONObject();
        message.put("message", "updatedDecks");
        message.put("updatedDecks", updatedDecks());
        return message;
    }

    public JSONObject successfulPlaceMessage(Player player) {
        JSONObject message = new JSONObject();
        message.put("message","successfulPlace");
        message.put("placementHistory", updatedPlacementHistory(player));
        message.put("updatedHand" , updatedHand(player));
        message.put("updatedResources", updatedResources(player));
        message.put("updatedScore", String.valueOf(player.getScore()));
        return message;
    }

    public JSONObject cannotPlaceMessage(String reason) {
        JSONObject message = new JSONObject();
        message.put("message","cannotPlace");
        message.put("reason", reason);
        return message;
    }

    public JSONObject updatedScoresMessage (ArrayList<String> names,ArrayList<Integer> updatedScores) {
        JSONObject message = new JSONObject();
        JSONArray scoreArray = new JSONArray();
        message.put("message","updatedScore");
        for (int i=0; i < names.size(); i++) {
            JSONObject personalScore = new JSONObject();
            personalScore.put("username", names.get(i));
            personalScore.put("score", updatedScores.get(i));
            scoreArray.add(personalScore);
        }
        message.put("scoreArray", scoreArray);
        return message;
    }

    /**
     * this message informs the players that they're playing the last round
     * @param reason what triggered the last round
     * @return the message that informs the players
     */
    public JSONObject lastRoundMessage(String reason) {
        JSONObject message = new JSONObject();
        message.put("message", "lastRound");
        message.put("reason", reason);
        return message;
    }

    /**
     * this message sends to the players their final scores when the game is ended
     * @param clientHandlers is the collection of current players
     * @return the final scores
     */
    public JSONObject leaderBoardMessage (ArrayList<ClientHandler> clientHandlers, GameController gameController) {
        JSONObject message = new JSONObject();
        message.put("message", "leaderBoard");
        if(clientHandlers.size()>= 1) {
            JSONObject player = new JSONObject();
            player.put("username",clientHandlers.get(0).getUsername());
            player.put("score", String.valueOf( gameController.getCurrentPlayer(clientHandlers.get(0)).getScore()));
            message.put("first", player);
        }
        else message.put("first", null);

        if(clientHandlers.size()>= 2) {
            JSONObject player = new JSONObject();
            player.put("username",clientHandlers.get(1).getUsername());
            player.put("score", String.valueOf( gameController.getCurrentPlayer(clientHandlers.get(1)).getScore()));
            message.put("second", player);
        }
        else message.put("second", null);

        if(clientHandlers.size()>= 3) {
            JSONObject player = new JSONObject();
            player.put("username",clientHandlers.get(2).getUsername());
            player.put("score", String.valueOf( gameController.getCurrentPlayer(clientHandlers.get(2)).getScore()));
            message.put("third", player);
        }
        else message.put("third", null);
        if(clientHandlers.size()>= 4) {
            JSONObject player = new JSONObject();
            player.put("username",clientHandlers.get(3).getUsername());
            player.put("score", String.valueOf( gameController.getCurrentPlayer(clientHandlers.get(3)).getScore()));
            message.put("fourth", player);
        }
        else message.put("fourth", null);
        return message;
    }

    /**
     * message sent when a client loses connection to inform the other clients that the game is getting cancelled
     * @return message
     */
    public JSONObject closingGameMessage () {
        JSONObject message = new JSONObject();
        message.put("message","closingGame");
        return message;
    }

    private JSONArray updatedPlacementHistory(Player player) {
        JSONArray placementHistory = new JSONArray();
        System.out.println(player.getGamefield().getPlacementHistory());
        for(PlaceableCard placeableCard : player.getGamefield().getPlacementHistory()) {
            JSONObject card = new JSONObject();
            card.put("cardID", String.valueOf(placeableCard.getId()));
            card.put("facingUp", String.valueOf(placeableCard.isFacingUp()));
            card.put("x", String.valueOf(placeableCard.getX()));
            card.put("y", String.valueOf(placeableCard.getY()));
            placementHistory.add(card);
        }
        return placementHistory;
    }

    private JSONArray updatedHand(Player player) {
        JSONArray handArray = new JSONArray();
        for(PlaceableCard cardInHand : player.getHand()){
            System.out.println(cardInHand.getId());
            handArray.add(String.valueOf(cardInHand.getId()));
        }
        return handArray;
    }

    private JSONObject updatedDecks() {
        JSONObject decks = new JSONObject();
        decks.put("topDeckResourceCardID", String.valueOf(game.resourceCardDeck.getTopCardID()));
        decks.put("leftRevealedResourceCardID", String.valueOf(game.resourceCardDeck.getLeftRevealedCardID()));
        decks.put("rightRevealedResourceCardID", String.valueOf(game.resourceCardDeck.getRightRevealedCardID()));
        decks.put("topDeckGoldCardID", String.valueOf(game.goldCardDeck.getTopCardID()));
        decks.put("leftRevealedGoldCardID", String.valueOf(game.goldCardDeck.getLeftRevealedCardID()));
        decks.put("rightRevealedGoldCardID", String.valueOf(game.goldCardDeck.getRightRevealedCardID()));
        return decks;
    }

    private JSONArray updatedResources(Player player) {
        JSONArray updatedResources = new JSONArray();
        JSONObject fungiResources = new JSONObject();
        fungiResources.put("fungiResources", String.valueOf(player.getGamefield().getFungiCount()));
        JSONObject plantResources = new JSONObject();
        plantResources.put("plantResources", String.valueOf(player.getGamefield().getPlantCount()));
        JSONObject animalResources = new JSONObject();
        animalResources.put("animalResources", String.valueOf(player.getGamefield().getAnimalCount()));
        JSONObject insectResources = new JSONObject();
        insectResources.put("insectResources", String.valueOf(player.getGamefield().getInsectCount()));
        JSONObject scrollCount = new JSONObject();
        scrollCount.put("scrollCount", String.valueOf(player.getGamefield().getScrollCount()));
        JSONObject inkPotCount = new JSONObject();
        inkPotCount.put("inkPutCount", String.valueOf(player.getGamefield().getInkPotCount()));
        JSONObject featherCount = new JSONObject();
        featherCount.put("featherCount", String.valueOf(player.getGamefield().getFeatherCount()));

        updatedResources.add(fungiResources);
        updatedResources.add(plantResources);
        updatedResources.add(animalResources);
        updatedResources.add(insectResources);
        updatedResources.add(scrollCount);
        updatedResources.add(inkPotCount);
        updatedResources.add(featherCount);

        return updatedResources;
    }
}
