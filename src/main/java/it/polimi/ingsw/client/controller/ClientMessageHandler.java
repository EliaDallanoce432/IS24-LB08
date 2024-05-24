package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.GUI.viewControllers.utility.CardRepresentation;
import it.polimi.ingsw.util.supportclasses.ClientState;
import it.polimi.ingsw.util.supportclasses.Token;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for handling incoming JSON messages
 * from the server. It parses the message content and updates the client's internal models accordingly.
 */
public class ClientMessageHandler {

    /**
     * processes an incoming JSON message from the server
     * @param message The JSONObject representing the received message.
     */
    public void execute (JSONObject message) {

        System.out.println("executing message: " + message);
        switch (message.get("message").toString()) {

            //in-lobby messages
            case "joinedLobby" -> updateClientState(ClientState.LOBBY_STATE);
            case "usernameSet" -> updateUsername(message);
            case "usernameAlreadyTaken" -> showError("Username Already Taken");
            case "gameCreated", "joinGame" -> updateClientState(ClientState.GAME_SETUP_STATE);
            case "cannotCreateGame" -> showError(message.get("reason").toString());
            case "gameDoesNotExist" -> updateClientState(ClientState.ERROR_JOINING_STATE , "Game does not exist");
            case "gameIsFull" -> updateClientState(ClientState.ERROR_JOINING_STATE , "Game is full");
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
            case "lastRound" -> handleLastRound(message);
            case "leaderBoard" -> updateLeaderboard(message);
            default -> { //do nothing
            }
        }
    }

    /**
     * updates the client player's username based on the received message
     * @param message The message containing the updated username
     */
    private void updateUsername(JSONObject message) {
        PlayerModel.getInstance().setUsername(message.get("username").toString());
    }

    /**
     * displays an error message to the user.
     * @param errorMessage The message to be displayed as the error.
     */
    private void showError(String errorMessage) {
        StageManager.getCurrentViewController().showErrorMessage(errorMessage);
    }

    /**
     * updates the client's state
     * @param clientState The new state of the client.
     */
    private void updateClientState(ClientState clientState) {
        ClientStateModel.getInstance().setClientState(clientState);
    }

    /**
     * updates the client's state and sets a reason for the state change.
     * @param clientState The new state of the client.
     * @param reason The reason for the state change.
     */
    private void updateClientState(ClientState clientState, String reason) {
        ClientStateModel.getInstance().setClientState(clientState, reason);
    }

    /**
     * updates the list of available games retrieved from the server
     * @param message The JSONObject containing the list of games. The message is expected to have a "games" field containing a JSONArray of game names.
     */
    private void updateAvailableGames(JSONObject message) {
        JSONArray gamesArray = (JSONArray) message.get("games");
        ArrayList<String> games = new ArrayList<>();
        for (Object o : gamesArray) {
            JSONObject gameObj = (JSONObject) o;
            games.add(gameObj.get("nameAndPlayers").toString());
        }
        AvailableGamesModel.getInstance().setGames(games);
    }

    /**
     * updates the information about selectable cards based on the received message.
     * @param message The JSONObject containing information about selectable cards. The message is expected to have fields for "starterCardID", "objectiveCardID1", and "objectiveCardID2".
     */
    private void updateSelectableCards (JSONObject message) {
        int starterCardID = Integer.parseInt(message.get("starterCardID").toString());
        int objectiveCardID1 = Integer.parseInt(message.get("objectiveCardID1").toString());
        int objectiveCardID2 = Integer.parseInt(message.get("objectiveCardID2").toString());
        SelectableCardsModel.getInstance().setSelectableCardsId(starterCardID,new int[]{objectiveCardID1, objectiveCardID2});
    }

    /**
     * updates the client's game state based on the initial game board information received from the server.
     * @param message The JSONObject containing the initial game board state information.
     */
    private void updateInitialBoardState (JSONObject message) {

        //parsing the message...
        int objectiveCardID1 = Integer.parseInt( message.get("commonObjective1").toString());
        int objectiveCardID2 = Integer.parseInt( message.get("commonObjective2").toString());
        int secretObjectiveCardID = Integer.parseInt(message.get("secretObjectiveID").toString());
        ArrayList<CardRepresentation> initialPlacementHistory = getPlacementHistoryArray((JSONArray) message.get("placementHistory"));
        ArrayList<CardRepresentation> initialHand = getHandArray((JSONArray) message.get("hand"));
        JSONObject decksJSON = (JSONObject) message.get("decks");
        String firstPlayerUsername = message.get("firstPlayer").toString();
        JSONObject resourcesJSON = (JSONObject) message.get("resources");
        Token token = Token.parseColor(message.get("token").toString());

        //updating the model...

        PlayerModel.getInstance().setTurnPlayer(firstPlayerUsername);
        ObjectivesModel.getInstance().setCommonObjectives(new int[] {objectiveCardID1, objectiveCardID2});
        ObjectivesModel.getInstance().setSecretObjectiveId(secretObjectiveCardID);
        GameFieldModel.getInstance().updatePlacementHistory(initialPlacementHistory);
        HandModel.getInstance().updateCardsInHand(initialHand);
        PlayerModel.getInstance().setToken(token);
        updateDeckModelFromJSON(decksJSON);
        updateResourcesFromJSON(resourcesJSON);
        if(PlayerModel.getInstance().getUsername().equals(firstPlayerUsername)) {
            ClientStateModel.getInstance().setClientState(ClientState.PLACING_STATE);
        }
        else ClientStateModel.getInstance().setClientState(ClientState.NOT_PLACING_STATE);
    }

    /**
     * updates the client's deck information based on the received message.
     * @param message The JSONObject containing the updated deck information.
     */
    private void updateDecks(JSONObject message) {
        //parsing the message...
        JSONObject updatedDecks = (JSONObject) message.get("updatedDecks");
        //updating the model...
        updateDeckModelFromJSON(updatedDecks);
    }

    /**
     * updates the client's hand with the new cards received from the server.
     * @param message The JSONObject containing the updated hand information.
     */
    private void updateHand(JSONObject message) {

        //parsing the message...
        ArrayList<CardRepresentation> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));


        //updating the model...
        HandModel.getInstance().updateCardsInHand(updatedHand);

    }

    /**
     * processes the message containing all the information used to update the player's game-field
     * @param message containing all the necessary information
     */
    private void updateGameField(JSONObject message){

        ArrayList<CardRepresentation> placementHistory = getPlacementHistoryArray((JSONArray) message.get("placementHistory"));
        ArrayList<CardRepresentation> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));

        GameFieldModel.getInstance().updatePlacementHistory(placementHistory);
        HandModel.getInstance().updateCardsInHand(updatedHand);
        ScoreBoardModel.getInstance().setMyScore(Integer.parseInt(message.get("updatedScore").toString()));

        JSONObject updatedResources = (JSONObject) message.get("updatedResources");
        updateResourcesFromJSON(updatedResources);

        if (!PlayerModel.getInstance().isLastTurn()) ClientStateModel.getInstance().setClientState(ClientState.DRAWING_STATE);



    }

    /**
     * processes the message informing the player of an incorrect placement
     * @param message containing he reason why the placement failed
     */
    private void cannotPlaceHandler(JSONObject message) {
        HandModel.getInstance().rollback();
        GameFieldModel.getInstance().rollback(); //reloads the last update of the model
        showError(message.get("reason").toString());

    }

    /**
     * processes the message containing the information about the updated turn
     * @param message containing the updated turn
     */
    private void updateTurnPlayer (JSONObject message){

        String currentTurnPlayer = message.get("player").toString();

        PlayerModel.getInstance().setTurnPlayer(currentTurnPlayer);
        if(PlayerModel.getInstance().getUsername().equals(currentTurnPlayer)) {
            ClientStateModel.getInstance().setClientState(ClientState.PLACING_STATE);
        }
        else ClientStateModel.getInstance().setClientState(ClientState.NOT_PLACING_STATE);

    }

    /**
     * processes the message containing players' updated scores
     * @param message containing the information about players' scores
     */
    private void updateScores(JSONObject message) {

        HashMap<String, Integer> scores = new HashMap<>();
        JSONArray scoresArray = (JSONArray) message.get("updatedScores");
        for (Object o : scoresArray) {
            JSONObject scoreObj = (JSONObject) o;
            scores.put(scoreObj.get("username").toString(), Integer.parseInt(scoreObj.get("score").toString()));
        }
        ScoreBoardModel.getInstance().setScores(scores);

    }

    private void handleLastRound(JSONObject message){
        updateClientState(ClientState.LAST_TURN_STATE, message.get("reason").toString());
        PlayerModel.getInstance().setLastTurn(true);
    }

    /**
     * processes the message that updates the leaderboard
     * @param message containing the final results
     */
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

    /**
     * converts the JSON object containing the hand into an ArrayList
     * @param jsonArray contains the cards in a player's hand
     * @return an arraylist containing the player's hand
     */
    private static ArrayList<CardRepresentation> getHandArray(JSONArray jsonArray){
        ArrayList<CardRepresentation> hand = new ArrayList<>();

        for (Object o : jsonArray) {
            CardRepresentation vCard = new CardRepresentation(Integer.parseInt(o.toString()), true);
            hand.add(vCard);
        }

        return hand;
    }

    /**
     * converts the JSON object containing the PlacementHistory into an ArrayList
     * @param jsonArray contains the information about player's placement history
     * @return an arraylist containing the placement history
     */
    private static ArrayList<CardRepresentation> getPlacementHistoryArray(JSONArray jsonArray){
        ArrayList<CardRepresentation> placementHistory = new ArrayList<>();

        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            int cardId = Integer.parseInt(obj.get("cardID").toString());
            boolean faceUp = Boolean.parseBoolean(obj.get("facingUp").toString());
            CardRepresentation vCard = new CardRepresentation(cardId, faceUp);
            vCard.setX(Integer.parseInt(obj.get("x").toString()));
            vCard.setY(Integer.parseInt(obj.get("y").toString()));
            placementHistory.addLast(vCard);
        }

        return placementHistory;
    }

    /**
     * updates the model of the decks from the given JSON
     * @param decksJSON JSONObject containing the information about the drawable cards
     */
    private static void updateDeckModelFromJSON (JSONObject decksJSON){
        int resTop = Integer.parseInt(decksJSON.get("topDeckResourceCardID").toString());
        int goldTop = Integer.parseInt(decksJSON.get("topDeckGoldCardID").toString());
        int resLeft = Integer.parseInt(decksJSON.get("leftRevealedResourceCardID").toString());
        int goldLeft = Integer.parseInt(decksJSON.get("leftRevealedGoldCardID").toString());
        int resRight = Integer.parseInt(decksJSON.get("rightRevealedResourceCardID").toString());
        int goldRight = Integer.parseInt(decksJSON.get("rightRevealedGoldCardID").toString());

        DeckModel.getInstance().updateDecks(resTop,resLeft,resRight,goldTop,goldLeft,goldRight);

    }

    /**
     * updates the resources in the playerModel from the given JSON
     * @param updatedResources JSONObject containing the information about the updated resources
     */
    private static void updateResourcesFromJSON( JSONObject updatedResources){
        ScoreBoardModel.getInstance().setResources(
                Integer.parseInt(updatedResources.get("animalResources").toString()),
                Integer.parseInt(updatedResources.get("insectResources").toString()),
                Integer.parseInt(updatedResources.get("fungiResources").toString()),
                Integer.parseInt(updatedResources.get("plantResources").toString()),
                Integer.parseInt(updatedResources.get("featherCount").toString()),
                Integer.parseInt(updatedResources.get("scrollCount").toString()),
                Integer.parseInt(updatedResources.get("inkPotCount").toString())
        );

    }

    /**
     * adds an element to an arrayList only if it's not null
     * @param list the list to add the object to
     * @param obj the object to be added
     */

    private void addIfNotNull(ArrayList<JSONObject> list, Object obj) {
        if (obj != null ) {
            list.add((JSONObject) obj);
        }
    }




}
