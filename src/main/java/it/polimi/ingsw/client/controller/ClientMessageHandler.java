package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.VirtualCard;
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
            case "gameDoesNotExist" -> updateClientState(ClientState.WELCOME_STATE);
            case "availableGames" -> updateAvailableGames(message);

            //in-game messages

            case "cardsSelection" -> updateSelectableCards(message);
            case "startGame" -> updateInitialBoardState(message);
            case "updatedDecks" -> updateDecks(message);
            case "updatedHand" -> updateHand(message);
            case "successfulPlace" -> updateGameField(message);
            case "cannotPlace" -> cannotPlaceHandler(message);
            case "turnPlayerUpdate" -> updateTurnPlayer(message);
            case "updatedScore" -> updateScores(message);



            default -> { //do nothing
            }
        }
    }

    private void updateUsername(JSONObject message) {
        PlayerModel.getIstance().setUsername(message.get("username").toString());
    }

    private void showError(String errorMessage) {
        StageManager.getViewController().showMessage("ERROR: " + errorMessage);
    }

    private void updateClientState(ClientState clientState) {
        ClientStateModel.getIstance().setClientState(clientState);
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
        ArrayList<VirtualCard> initialPlacementHistory = getPlacementHistoryArray((JSONArray) message.get("placementHistory"));
        ArrayList<VirtualCard> initialHand = getHandArray((JSONArray) message.get("hand"));
        JSONObject decksJSON = (JSONObject) message.get("decks");
        String firstPlayerUsername = message.get("firstPlayer").toString();

        Color token = Color.parseColor(message.get("token").toString());

        //updating the model...
        if(PlayerModel.getIstance().getUsername().equals(firstPlayerUsername)) {
            ClientStateModel.getIstance().setClientState(ClientState.PLAYING_STATE);
        }
        else ClientStateModel.getIstance().setClientState(ClientState.NOT_PLAYING_STATE);
        PlayerModel.getIstance().setTurnPlayer(firstPlayerUsername);
        ObjectivesModel.getIstance().setCommonObjectives(new int[] {objectiveCardID1, objectiveCardID2});
        ObjectivesModel.getIstance().setSecretObjectiveId(secretObjectiveCardID);
        GameFieldModel.getIstance().updatePlacementHistory(initialPlacementHistory);
        HandModel.getIstance().updateCardsInHand(initialHand);
        PlayerModel.getIstance().setToken(token);
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
        ArrayList<VirtualCard> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));


        //updating the model...
        HandModel.getIstance().updateCardsInHand(updatedHand);

    }

    private void updateGameField(JSONObject message){

        ArrayList<VirtualCard> placementHistory = getPlacementHistoryArray((JSONArray) message.get("placementHistory"));
        ArrayList<VirtualCard> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));

        GameFieldModel.getIstance().updatePlacementHistory(placementHistory);
        ClientStateModel.getIstance().setClientState(ClientState.DRAWING_STATE);
        HandModel.getIstance().updateCardsInHand(updatedHand);


        //TODO update scores and resources
    }

    private void cannotPlaceHandler(JSONObject message) {
        HandModel.getIstance().rollback();
        GameFieldModel.getIstance().rollback(); //reloads the last update of the model
        showError(message.get("reason").toString());

    }

    private void updateTurnPlayer (JSONObject message){

        String currentTurnPlayer = message.get("player").toString();

        PlayerModel.getIstance().setTurnPlayer(currentTurnPlayer);
        if(PlayerModel.getIstance().getUsername().equals(currentTurnPlayer)) {
            ClientStateModel.getIstance().setClientState(ClientState.PLAYING_STATE);
        }
        else ClientStateModel.getIstance().setClientState(ClientState.NOT_PLAYING_STATE);

    }

    private void updateScores(JSONObject message) {

        HashMap<String, Integer> scores = new HashMap<>();
        JSONArray scoresArray = (JSONArray) message.get("scoreArray");
        for (Object o : scoresArray) {
            JSONObject scoreObj = (JSONObject) o;
            scores.put(scoreObj.get("username").toString(), Integer.parseInt(scoreObj.get("score").toString()));
        }
        ScoreBoardModel.getIstance().setScores(scores);

    }






    //Utility methods

    private static ArrayList<VirtualCard> getHandArray(JSONArray jsonArray){
        ArrayList<VirtualCard> hand = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            VirtualCard vCard = new VirtualCard(Integer.parseInt(jsonArray.get(i).toString()),true);
            hand.add(vCard);
        }

        return hand;
    }

    private static ArrayList<VirtualCard> getPlacementHistoryArray(JSONArray jsonArray){
        ArrayList<VirtualCard> placementHistory = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            int cardId = Integer.parseInt(obj.get("cardID").toString());
            boolean faceUp = Boolean.parseBoolean(obj.get("facingUp").toString());
            VirtualCard vCard = new VirtualCard(cardId,faceUp);
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




}
