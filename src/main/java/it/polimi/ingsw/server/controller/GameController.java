package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.network.ServerNetworkObserverInterface;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Color;
import it.polimi.ingsw.util.supportclasses.GameState;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameController implements Runnable, ServerNetworkObserverInterface, GameObserver {
    private final String gameName;
    private final int numberOfExpectedPlayers;
    private volatile ArrayList<ClientHandler> clientHandlers;
    private final Lobby lobby;
    private final Game game;
    private int turn;
    private final List<Request> requests;
    private boolean running;
    private final GameControllerRequestExecutor gameControllerRequestExecutor;

    public GameController(Lobby lobby, int numberOfExpectedPlayers, String gameName) {
        this.gameName = gameName;
        this.clientHandlers = new ArrayList<>();
        this.numberOfExpectedPlayers = numberOfExpectedPlayers;
        this.lobby = lobby;
        this.requests = Collections.synchronizedList(new ArrayList<>());
        this.running = true;
        this.gameControllerRequestExecutor = new GameControllerRequestExecutor(this);
        this.game = Game.getInstance(numberOfExpectedPlayers, this);
        System.out.println(gameName + " is ready");
    }

    public int getNumberOfPlayers() {
        return numberOfExpectedPlayers;
    }
    public void run() {
        while (running) {
            while (!requests.isEmpty()) {
                gameControllerRequestExecutor.execute(requests.getFirst());
                requests.removeFirst();
            }
        }
    }
    @Override
    public void addNewRequest(Request request) {
        requests.addLast(request);
        System.out.println("New request added to the lobby");
    }


    /**
     * adds the player to the arraylist of players
     * @param player who joined the current game
     */
    public synchronized void enterGame (ClientHandler player){
        clientHandlers.add(player);
        player.setGame(this);
        player.setInGame(true);
    }

    /**
     * removes a player from the current game and sends him to the lobby
     * @param player who left the game
     */
    public synchronized void leaveGame (ClientHandler player){
        clientHandlers.remove(player);
        player.setGame(null);
        player.setInGame(false);
        lobby.enterLobby(player);
    }

    /**
     * initializes the players in the model and sets the relative tokens for the clients
     */
    private void gameInitialize()
    {
        List<Color> tokens = Arrays.asList(Color.blue, Color.yellow, Color.red, Color.green);
        Collections.shuffle(tokens); //randomize color token player
        Collections.shuffle(clientHandlers); //randomize first player
        for (int i = 0; i < numberOfExpectedPlayers; i++) {
            game.players.put(tokens.get(i), new Player(clientHandlers.get(i).getUsername(), tokens.get(i)));
            clientHandlers.get(i).setToken(tokens.get(i));
        }
    }

    /**
     * draws a card for every player
     */
    private void starterCardsSelectionPreparation() {
        StarterCard starterCard=null;
        for(Player p : game.getPlayers()) {
            try {
                starterCard = (StarterCard) game.starterCardDeck.directDraw() ;
            } catch (EmptyDeckException ignored) {
                System.out.println(gameName + " has no drawn starter cards");
            }
            p.setStarterCard(starterCard);
        }
    }

    /**
     * draws 2 objective cards for each player
     */
    private void secretObjectiveCardsSelectionPreparation()
    {
        for (Player p : game.getPlayers()) {
            try {
                ObjectiveCard cardTemp1 = ((ObjectiveCard) game.objectiveCardDeck.directDraw());
                ObjectiveCard cardTemp2 = ((ObjectiveCard) game.objectiveCardDeck.directDraw());
                p.setDrawnObjectiveCards(new ObjectiveCard[]{cardTemp1, cardTemp2});
            } catch (EmptyDeckException ignored) {
            }
        }
    }

    /**
     * prepares the game to wait for players to choose the starter card orientation and the secret objective
     */
    private void gamePreparation (){
        gameInitialize();
        starterCardsSelectionPreparation();
        secretObjectiveCardsSelectionPreparation();
    }

    /**
     * sets the flag ready to true when the player is ready to play
     * @param player player that is now ready
     */
    public synchronized void ready(ClientHandler player){
        getCurrentPlayer(player).setReady(true);
    }

    /**
     * communicates to players the game is about to start and sends their cards
     */
    public void startGame () {
        for (ClientHandler client : clientHandlers) {
            Player currentPlayer = getCurrentPlayer(client);
            ArrayList<PlaceableCard> hand = currentPlayer.getHand();
            ArrayList<Card> visibleCardsinResourceCardsDeck = game.resourceCardDeck.getVisibleCards();
            ArrayList<Card> visibleCardsinGoldDeck = game.goldCardDeck.getVisibleCards();
            StarterCard starterCard = currentPlayer.getStarterCard();
            ObjectiveCard secretObjective = currentPlayer.getSecretObjective();
            ArrayList<ObjectiveCard> commonObjectives = game.commonObjectives;

            client.send(ServerMessageGenerator.startGameMessage(hand,visibleCardsinResourceCardsDeck,visibleCardsinGoldDeck,starterCard,secretObjective,commonObjectives));
        }
        for (ClientHandler player : clientHandlers)
            player.clearTurnState();
    }

    /**
     * sends the updated scores to every player
     */
    public void sendUpDatedScores () {
        ArrayList<Integer> scores = new ArrayList<>();
        for (int i = 0; i < numberOfExpectedPlayers; i++) {
            scores.add(game.getPlayers().get(i).getScore());
        }

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < numberOfExpectedPlayers; i++) {
            names.add(game.getPlayers().get(i).getUsername());
        }

        for (int i = 0; i < numberOfExpectedPlayers; i++) {
            clientHandlers.get(i).send(ServerMessageGenerator.generateUpdatedScoreMessage(names, scores), true);
        }
    }

    public synchronized void place (ClientHandler player,int placeableCardId, boolean facingUp, int x, int y) throws CannotPlaceCardException, NotYourTurnException {
        if(turn != clientHandlers.indexOf(player)) {
            throw new NotYourTurnException();
        }
        PlaceableCard cardInHand = null;
        Player currentPlayer = getCurrentPlayer(player);
        //seleziona carta dalla mano
        for (PlaceableCard card: currentPlayer.getHand()) {
            if (card.getId() == placeableCardId) {
                cardInHand = card;
                break;
            }
        }
        assert cardInHand != null;
        currentPlayer.getGamefield().place(cardInHand, facingUp, x, y);
        //currentPlayer.place(cardInHand, facingUp, x, y);
        player.setAlreadyPlaced(true);
    }

    public synchronized void place (ClientHandler player,int starterCardId, boolean facingUp){
        Player currentPlayer = getCurrentPlayer(player);
        StarterCard starterCard = currentPlayer.getStarterCard();
        currentPlayer.getGamefield().place(starterCard, facingUp);
       // currentPlayer.place(starterCard, facingUp);
    }

    public synchronized void directDrawResourceCard (ClientHandler player) throws NotYourTurnException,
            EmptyDeckException, FullHandException, CannotDrawException {

        if (turn != clientHandlers.indexOf(player)) {
            throw new NotYourTurnException();
        }

        if (!player.hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }
        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.directDraw();

        getCurrentPlayer(player).addToHand(cardTemp);
        passTurn(player);
    }
    public synchronized void directDrawGoldCard (ClientHandler player) throws EmptyDeckException, FullHandException, NotYourTurnException, CannotDrawException {

        if (turn != clientHandlers.indexOf(player)) {
            throw new NotYourTurnException();
        }
        if (!player.hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        GoldCard cardTemp = (GoldCard) game.goldCardDeck.directDraw();
        getCurrentPlayer(player).addToHand(cardTemp);
        passTurn(player);
    }
    public synchronized void drawLeftRevealedResourceCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {

        if (turn != clientHandlers.indexOf(player)) {
            throw new NotYourTurnException();
        }

        if (!player.hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }
        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getLeftRevealedCard();
        getCurrentPlayer(player).addToHand(cardTemp);
        passTurn(player);
    }
    public synchronized void drawRightRevealedResourceCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (turn != clientHandlers.indexOf(player)) {
            throw new NotYourTurnException();
        }
        if (!player.hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getRightRevealedCard();
        getCurrentPlayer(player).addToHand(cardTemp);
        passTurn(player);
    }
    public synchronized void drawLeftRevealedGoldCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {

        if (turn != clientHandlers.indexOf(player)) {
            throw new NotYourTurnException();
        }
        if (!player.hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }
        GoldCard cardTemp = (GoldCard) game.goldCardDeck.getLeftRevealedCard();
        getCurrentPlayer(player).addToHand(cardTemp);
        passTurn(player);
    }
    public synchronized void drawRightRevealedGoldCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {

        if (turn != clientHandlers.indexOf(player)) {
            throw new NotYourTurnException();
        }
        if (!player.hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }
        GoldCard cardTemp = (GoldCard) game.goldCardDeck.getRightRevealedCard();
        getCurrentPlayer(player).addToHand(cardTemp);
        passTurn(player);
    }

    /**
     * selects the orientation of the starter card and places it
     * @param player player that selected the orientation
     * @param starterCardId starter card id
     * @param facingUp orientation: true if the front is facing up
     */
    public synchronized void chooseStarterCardOrientations (ClientHandler player,int starterCardId, boolean facingUp) {
        Player currentPlayer = getCurrentPlayer(player);
        if (currentPlayer.getStarterCard().getId() == starterCardId) {
            currentPlayer.place(currentPlayer.getStarterCard(), facingUp);
            System.out.println("il player " + player.getUsername() + "ha scelto facingup = " + facingUp);
            currentPlayer.setStarterCardOrientationSelected(true);
        }
    }

    /**
     * selects the chosen secret objective between the two drawn objective cards
     * @param player player choosing the secret objective
     * @param objectiveCardId chosen secret objective card id
     */
    public synchronized void chooseSecretObjectiveCard (ClientHandler player,int objectiveCardId){
        Player currentPlayer = getCurrentPlayer(player);
        for(ObjectiveCard drawnObjectiveCard : currentPlayer.getDrawnObjectiveCards())
            if (drawnObjectiveCard.getId() == objectiveCardId) {
                currentPlayer.setSecretObjective(drawnObjectiveCard);
                System.out.println("il player " + player.getUsername() + "ha scelto la objective card " + objectiveCardId);
            }
    }

    public Player getCurrentPlayer(ClientHandler player)
    {
        return game.players.get(player.getToken());
    }

    private void passTurn (ClientHandler player){
        turn = (turn + 1) % clientHandlers.size();
        player.clearTurnState();
    }

    private void broadcast (JSONObject message){
        for (ClientHandler player : clientHandlers) {
            player.send(message);
        }
    }

    @Override
    public void notifyIncomingMessage (ClientHandler clientHandler){
       /* try {
            GameControllerRequestExecutor.execute(this, clientHandler.getReceivedRequest(), clientHandler);
        } catch (EmptyDeckException | CannotPlaceCardException | FullHandException e) {
            throw new RuntimeException(e);
        }*/
    }

    @Override
    public synchronized void notifyConnectionLoss (ClientHandler clientHandler){
        //TODO migliorare l'implementazione
        leaveGame(clientHandler);
        lobby.notifyConnectionLoss(clientHandler);
        while (!clientHandlers.isEmpty()) {
            Thread.onSpinWait();
        } //waits for everyone to leave back to the lobby
        running = false;
    }

    @Override
    public void notifyReady() {
        if(game.getGameState()!=GameState.waitingForPlayers) {
            return;
        }
        for (Player p : game.getPlayers()) {
            if(!p.isReady())
                return;
        }
        game.setGameState(GameState.waitingForCardsSelection);
        gamePreparation();
        for (ClientHandler c : clientHandlers) {
            StarterCard starterCard = getCurrentPlayer(c).getStarterCard();
            ObjectiveCard objectiveCard1 = getCurrentPlayer(c).getDrawnObjectiveCards()[0];
            ObjectiveCard objectiveCard2 = getCurrentPlayer(c).getDrawnObjectiveCards()[1];
            c.send(ServerMessageGenerator.cardsSelectionMessage(starterCard, objectiveCard1, objectiveCard2));
        }
    }

    @Override
    public void notifyStarterCardAndSecretObjetiveSelected() {
        for (ClientHandler player : clientHandlers) {
            Player currentPlayer = getCurrentPlayer(player);
            if(!currentPlayer.isStarterCardOrientationSelected() || currentPlayer.getSecretObjective() == null) return;
        }
        game.setGameState(GameState.playing);
        startGame();
    }

    /**
     * this method invokes the calculateFinalScore method set in the model of each player
     */
    public void calculateFinalScore() {
        for (Player p : game.getPlayers()) {
            p.calculateFinalScore();
        }
        //TODO caso di parità
    }

}



//    private void broadcast(JSONObject message, ClientHandler  disconnectedPlayer) {
//        for (ClientHandler player : clients) {
//            if(player == disconnectedPlayer) continue;
//            player.send(message);
//        }
//    }

