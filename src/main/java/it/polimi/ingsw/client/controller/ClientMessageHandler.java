package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.utility.CardRepresentation;
import it.polimi.ingsw.util.supportclasses.ClientState;
import it.polimi.ingsw.util.supportclasses.Token;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for handling incoming JSON messages from the server.
 * It parses the message content and updates the client's internal models accordingly.
 */
public class ClientMessageHandler {

    /**
     * Processes an incoming JSON message from the server.
     * @param message The JSONObject representing the received message.
     */
    public void execute (JSONObject message) {

        switch (message.get("message").toString()) {
            //in-lobby messages
            case "joinedLobby" -> updateClientState(ClientState.LOBBY_STATE, "Joined Lobby");
            case "usernameSet" -> updateUsername(message);
            case "usernameAlreadyTaken" -> showError("Username Already Taken");
            case "gameCreated" -> updateClientState(ClientState.GAME_SETUP_STATE, "Game Created Successfully");
            case "joinGame" -> updateClientState(ClientState.GAME_SETUP_STATE, "Joined Game Successfully");
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
     * Updates the client player's username based on the received message.
     * @param message The message containing the updated username.
     */
    private void updateUsername(JSONObject message) {
        PlayerModel.getInstance().setUsername(message.get("username").toString());
    }

    /**
     * Displays an error message to the user.
     * @param errorMessage The message to be displayed as the error.
     */
    private void showError(String errorMessage) {
        StageManager.getCurrentViewController().showErrorMessage(errorMessage);
    }

    /**
     * Updates the client's state.
     * @param clientState The new state of the client.
     */
    @SuppressWarnings("SameParameterValue")
    private void updateClientState(ClientState clientState) {
        ClientStateModel.getInstance().setClientState(clientState);
    }

    /**
     * Updates the client's state and sets a reason for the state change.
     * @param clientState The new state of the client.
     * @param reason The reason for the state change.
     */
    private void updateClientState(ClientState clientState, String reason) {
        ClientStateModel.getInstance().setClientState(clientState, reason);
    }

    /**
     * Updates the list of available games retrieved from the server.
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
     * Updates the information about selectable cards based on the received message.
     * @param message The JSONObject containing information about selectable cards. The message is expected to have fields for "starterCardID", "objectiveCardID1", and "objectiveCardID2".
     */
    private void updateSelectableCards (JSONObject message) {
        int starterCardID = Integer.parseInt(message.get("starterCardID").toString());
        int objectiveCardID1 = Integer.parseInt(message.get("objectiveCardID1").toString());
        int objectiveCardID2 = Integer.parseInt(message.get("objectiveCardID2").toString());
        SelectableCardsModel.getInstance().setSelectableCardsId(starterCardID,new int[]{objectiveCardID1, objectiveCardID2});
    }

    /**
     * Updates the client's game state based on the initial game information received from the server.
     * @param message The JSONObject containing the initial game state information.
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
        Token token = Token.parseToken(message.get("token").toString());
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
        else ClientStateModel.getInstance().setClientState(ClientState.NOT_PLAYING_STATE);
    }

    /**
     * Updates the client's deck information based on the received message.
     * @param message The JSONObject containing the updated deck information.
     */
    private void updateDecks(JSONObject message) {
        //parsing the message...
        JSONObject updatedDecks = (JSONObject) message.get("updatedDecks");
        //updating the model...
        updateDeckModelFromJSON(updatedDecks);
    }

    /**
     * Updates the client's hand with the new cards received from the server.
     * @param message The JSONObject containing the updated hand information.
     */
    private void updateHand(JSONObject message) {
        //parsing the message...
        ArrayList<CardRepresentation> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));
        //updating the model...
        HandModel.getInstance().updateCardsInHand(updatedHand);
    }

    /**
     * Processes the message containing all the information used to update the player's game-field.
     * If it's not the last round it updates the client state to drawing state.
     * @param message The JSONObject containing all the necessary information.
     */
    private void updateGameField(JSONObject message){
        //parsing the message...
        ArrayList<CardRepresentation> placementHistory = getPlacementHistoryArray((JSONArray) message.get("placementHistory"));
        ArrayList<CardRepresentation> updatedHand = getHandArray((JSONArray) message.get("updatedHand"));
        JSONObject updatedResources = (JSONObject) message.get("updatedResources");
        //updating the model...
        GameFieldModel.getInstance().updatePlacementHistory(placementHistory);
        HandModel.getInstance().updateCardsInHand(updatedHand);
        ScoreBoardModel.getInstance().setMyScore(Integer.parseInt(message.get("updatedScore").toString()));
        updateResourcesFromJSON(updatedResources);

        if (!PlayerModel.getInstance().isLastRound()) ClientStateModel.getInstance().setClientState(ClientState.DRAWING_STATE);
    }

    /**
     * Processes the message informing the player of an incorrect placement.
     * @param message The JSONObject containing he reason why the placement failed.
     */
    private void cannotPlaceHandler(JSONObject message) {
        HandModel.getInstance().rollback();
        GameFieldModel.getInstance().rollback(); //reloads the last update of the model
        showError(message.get("reason").toString());
    }

    /**
     * Processes the message containing the information about the updated turn and updates the client state accordingly.
     * @param message The JSONObject containing the username of the turn player.
     */
    private void updateTurnPlayer (JSONObject message){
        String currentTurnPlayer = message.get("player").toString();

        PlayerModel.getInstance().setTurnPlayer(currentTurnPlayer);
        if(PlayerModel.getInstance().getUsername().equals(currentTurnPlayer)) {
            ClientStateModel.getInstance().setClientState(ClientState.PLACING_STATE);
        }
        else ClientStateModel.getInstance().setClientState(ClientState.NOT_PLAYING_STATE);
    }

    /**
     * Processes the message containing players' updated scores.
     * @param message The JSONObject containing the players' scores.
     */
    private void updateScores(JSONObject message) {
        //parsing the message...
        HashMap<String, Integer> scores = new HashMap<>();
        HashMap<String, Token> tokens = new HashMap<>();
        JSONArray scoresArray = (JSONArray) message.get("updatedScores");
        //updating the model...
        for (Object o : scoresArray) {
            JSONObject scoreObj = (JSONObject) o;
            tokens.put(scoreObj.get("username").toString(), Token.parseToken(scoreObj.get("token").toString()));
            scores.put(scoreObj.get("username").toString(), Integer.parseInt(scoreObj.get("score").toString()));
        }
        ScoreBoardModel.getInstance().setTokens(tokens);
        ScoreBoardModel.getInstance().setScores(scores);
    }

    /**
     * Processes the message containing the last round message.
     * @param message The JSONObject containing the last round message.
     */
    private void handleLastRound(JSONObject message){
        updateClientState(ClientState.LAST_ROUND_STATE, message.get("reason").toString());
        PlayerModel.getInstance().setLastTurn(true);
    }

    /**
     * Processes the message that updates the leaderboard.
     * @param message The JSONObject containing the final results.
     */
    @SuppressWarnings("unchecked")
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
     * Converts the JSONObject containing the hand into an ArrayList.
     * @param jsonArray The JSONObject contains the cards in a player's hand.
     * @return An ArrayList containing the player's hand.
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
     * Converts the JSONArray containing the PlacementHistory into an ArrayList.
     * @param jsonArray The JSONObject contains the information about player's placement history.
     * @return An ArrayList containing the placement history.
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
     * Updates the model of the decks from the given JSONObject.
     * @param decksJSON The JSONObject containing the IDs of the drawable cards.
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
     * Updates the resources in the playerModel from the given JSONObject.
     * @param updatedResources The JSONObject containing the information about the updated resources.
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
     * Adds an element to an ArrayList only if it's not null.
     * @param list The List to add the object to.
     * @param obj The object to be added.
     */
    private void addIfNotNull(ArrayList<JSONObject> list, Object obj) {
        if (obj != null ) {
            list.add((JSONObject) obj);
        }
    }
}
