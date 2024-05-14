package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.utility.CardRepresentation;
import it.polimi.ingsw.util.supportclasses.ClientState;
import it.polimi.ingsw.util.supportclasses.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientMessageHandler {

    public void execute (JSONObject message) {

        System.out.println("executing message: " + message);

        switch (message.get("message").toString()) {

            //in-lobby messages

            case "usernameSet" -> updateUsername(message);
            case "usernameAlreadyTaken" -> showError("Username Already Taken");
            case "gameCreated" -> updateClientState(ClientState.WAITING_STATE);
            case "joinGame" -> updateClientState(ClientState.WAITING_STATE);
            case "gameDoesNotExist" -> updateClientState(ClientState.ERROR_JOINING_STATE);
            case "availableGames" -> updateAvailableGames(message);

            //in-game messages

            case "cardsSelection" -> updateSelectableCards(message);
            case "startGame" -> updateInitialBoardState(message);
            case "updatedDecks" -> updateDecks(message);
            case "updatedHand" -> updateHand(message);
            case "successfulPlace" -> updateGameField(message);
            case "cannotPlace" -> cannotPlaceHandler(message);
            case "turnPlayerUpdate" -> updateTurnPlayer(message);
            case "updatedScores" -> updateScores(message);
            case "closingGame" -> updateClientState(ClientState.KICKED_STATE);
            case "lastRound" -> updateClientState(ClientState.LAST_TURN_STATE, message.get("reason").toString());
            case "leaderBoard" -> updateLeaderboard(message);
            default -> { //do nothing
            }
        }
    }

    private void updateUsername(JSONObject message) {
        PlayerModel.getInstance().setUsername(message.get("username").toString());
    }

    private void showError(String errorMessage) {
        StageManager.getViewController().showErrorMessage(errorMessage);
    }

    private void updateClientState(ClientState clientState) {
        ClientStateModel.getInstance().setClientState(clientState);
    }

    private void updateClientState(ClientState clientState, String reason) {
        ClientStateModel.getInstance().setClientState(clientState, reason);
    }

    private void updateAvailableGames(JSONObject message) {
        JSONArray gamesArray = (JSONArray) message.get("games");
        ArrayList<String> games = new ArrayList<>();
        for (int i = 0; i < gamesArray.size(); i++) {
            JSONObject gameObj = (JSONObject) gamesArray.get(i);
            games.add(gameObj.get("name").toString());
        }
        AvailableGamesModel.getIstance().setGames(games);
    }

    private void updateSelectableCards (JSONObject message) {
        int starterCardID = Integer.parseInt(message.get("starterCardID").toString());
        int objectiveCardID1 = Integer.parseInt(message.get("objectiveCardID1").toString());
        int objectiveCardID2 = Integer.parseInt(message.get("objectiveCardID2").toString());
        SelectableCardsModel.getIstance().setStarterCardId(starterCardID);
        SelectableCardsModel.getIstance().setSelectableObjectiveCardsId(new int[]{objectiveCardID1, objectiveCardID2});
    }

    private void updateInitialBoardState (JSONObject message) {

        //parsing the message...
        int objectiveCardID1 = Integer.parseInt( message.get("commonObjective1").toString());
        int objectiveCardID2 = Integer.parseInt( message.get("commonObjective2").toString());
        int secretObjectiveCardID = Integer.parseInt(message.get("secretObjectiveID").toString());
        ArrayList<CardRepresentation> initialPlacementHistory = getPlacementHistoryArray((JSONArray) message.get("placementHistory"));
        ArrayList<CardRepresentation> initialHand = getHandArray((JSONArray) message.get("hand"));
        JSONObject decksJSON = (JSONObject) message.get("decks");
        String firstPlayerUsername = message.get("firstPlayer").toString();

        Color token = Color.parseColor(message.get("token").toString());

        //updating the model...
        if(PlayerModel.getInstance().getUsername().equals(firstPlayerUsername)) {
            ClientStateModel.getInstance().setClientState(ClientState.PLAYING_STATE);
        }
        else ClientStateModel.getInstance().setClientState(ClientState.NOT_PLAYING_STATE);
        PlayerModel.getInstance().setTurnPlayer(firstPlayerUsername);
        ObjectivesModel.getIstance().setCommonObjectives(new int[] {objectiveCardID1, objectiveCardID2});
        ObjectivesModel.getIstance().setSecretObjectiveId(secretObjectiveCardID);
        GameFieldModel.getIstance().updatePlacementHistory(initialPlacementHistory);
        HandModel.getIstance().updateCardsInHand(initialHand);
        PlayerModel.getInstance().setToken(token);
        updateDeckModelFromJSON(decksJSON);

    }

    private void updateDecks(JSONObject message) {
        //parsing the message...
        JSONObject updatedDecks = (JSONObject) message.get("updatedDecks");
        //updating the model...
        updateDeckModelFromJSON(updatedDecks);
    }

    private void updateHand(JSONObject message) {

        //parsing the message...
        ArrayList<CardRepresentation> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));


        //updating the model...
        HandModel.getIstance().updateCardsInHand(updatedHand);

    }

    private void updateGameField(JSONObject message){

        ArrayList<CardRepresentation> placementHistory = getPlacementHistoryArray((JSONArray) message.get("placementHistory"));
        ArrayList<CardRepresentation> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));

        GameFieldModel.getIstance().updatePlacementHistory(placementHistory);
        if (ClientStateModel.getInstance().getClientState() != ClientState.LAST_TURN_STATE) ClientStateModel.getInstance().setClientState(ClientState.DRAWING_STATE);
        HandModel.getIstance().updateCardsInHand(updatedHand);
        ScoreBoardModel.getInstance().setMyScore(Integer.parseInt(message.get("updatedScore").toString()));


        //TODO update resources
    }

    private void cannotPlaceHandler(JSONObject message) {
        HandModel.getIstance().rollback();
        GameFieldModel.getIstance().rollback(); //reloads the last update of the model
        showError(message.get("reason").toString());

    }

    private void updateTurnPlayer (JSONObject message){

        String currentTurnPlayer = message.get("player").toString();

        PlayerModel.getInstance().setTurnPlayer(currentTurnPlayer);
        if(PlayerModel.getInstance().getUsername().equals(currentTurnPlayer)) {
            ClientStateModel.getInstance().setClientState(ClientState.PLAYING_STATE);
        }
        else ClientStateModel.getInstance().setClientState(ClientState.NOT_PLAYING_STATE);

    }

    private void updateScores(JSONObject message) {

        HashMap<String, Integer> scores = new HashMap<>();
        JSONArray scoresArray = (JSONArray) message.get("updatedScores");
        for (Object o : scoresArray) {
            JSONObject scoreObj = (JSONObject) o;
            scores.put(scoreObj.get("username").toString(), Integer.parseInt(scoreObj.get("score").toString()));
        }
        ScoreBoardModel.getInstance().setScores(scores);

    }

    private void updateLeaderboard(JSONObject message) {
        ArrayList<JSONObject> leaderboard = new ArrayList<>();

        addIfNotNull(leaderboard, message.getOrDefault("first",null));
        addIfNotNull(leaderboard, message.getOrDefault("second",null));
        addIfNotNull(leaderboard, message.getOrDefault("third",null));
        addIfNotNull(leaderboard, message.getOrDefault("fourth",null));

        ScoreBoardModel.getInstance().setLeaderboard(leaderboard);
        ClientStateModel.getInstance().setClientState(ClientState.END_GAME_STATE);
    }





    //Utility methods

    private static ArrayList<CardRepresentation> getHandArray(JSONArray jsonArray){
        ArrayList<CardRepresentation> hand = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            CardRepresentation vCard = new CardRepresentation(Integer.parseInt(jsonArray.get(i).toString()),true);
            hand.add(vCard);
        }

        return hand;
    }

    private static ArrayList<CardRepresentation> getPlacementHistoryArray(JSONArray jsonArray){
        ArrayList<CardRepresentation> placementHistory = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            int cardId = Integer.parseInt(obj.get("cardID").toString());
            boolean faceUp = Boolean.parseBoolean(obj.get("facingUp").toString());
            CardRepresentation vCard = new CardRepresentation(cardId,faceUp);
            vCard.setX(Integer.parseInt(obj.get("x").toString()));
            vCard.setY(Integer.parseInt(obj.get("y").toString()));
            placementHistory.addLast(vCard);
        }

        return placementHistory;
    }

    private static void updateDeckModelFromJSON (JSONObject decksJSON){
        int resTop = Integer.parseInt(decksJSON.get("topDeckResourceCardID").toString());
        int goldTop = Integer.parseInt(decksJSON.get("topDeckGoldCardID").toString());
        int resLeft = Integer.parseInt(decksJSON.get("leftRevealedResourceCardID").toString());
        int goldLeft = Integer.parseInt(decksJSON.get("leftRevealedGoldCardID").toString());
        int resRight = Integer.parseInt(decksJSON.get("rightRevealedResourceCardID").toString());
        int goldRight = Integer.parseInt(decksJSON.get("rightRevealedGoldCardID").toString());

        DeckModel.getIstance().updateDecks(resTop,resLeft,resRight,goldTop,goldLeft,goldRight);

    }

    private void addIfNotNull(ArrayList<JSONObject> list, Object obj) {
        if (obj != null ) {
            list.add((JSONObject) obj);
        }
    }




}
