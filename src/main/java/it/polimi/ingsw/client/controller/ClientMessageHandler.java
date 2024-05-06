package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ClientMessageHandler {

    public void execute (JSONObject message) {
        switch (message.get("message").toString()) {
            case "usernameSet" -> updateUsername(message);
            case "usernameAlreadyTaken" -> showError("Username Already Taken");
            case "gameCreated" -> updateClientState(ClientState.WAITING_STATE);
            case "joinGame" -> updateClientState(ClientState.WAITING_STATE);
            case "gameDoesNotExist" -> updateClientState(ClientState.WELCOME_STATE);
            case "availableGames" -> updateAvailableGames(message);
            case "cardsSelection" -> updateSelectableCards(message);
            case "setupGame" -> updateInitialBoardState(message);

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
        int starterCardID = Integer.parseInt( message.get("starterCardID").toString());
        boolean starterCardOrientation = Boolean.parseBoolean(message.get("starterCardOrientation").toString());
        int objectiveCardID1 = Integer.parseInt( message.get("commonObjectiveCardID1").toString());
        int objectiveCardID2 = Integer.parseInt( message.get("commonObjectiveCardID2").toString());
        int secretObjectiveCardID = Integer.parseInt(message.get("commonSecretObjectiveCardID").toString());
        ObjectivesModel.getIstance().setCommonObjectives(new int[] {objectiveCardID1, objectiveCardID2});
        ObjectivesModel.getIstance().setSecretObjectiveId(secretObjectiveCardID);
        GameFieldModel.getIstance().setStarterCard(starterCardID, starterCardOrientation);

    }




}
