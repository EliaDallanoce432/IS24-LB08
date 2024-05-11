package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.network.ServerNetworkObserverInterface;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.GameState;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController implements Runnable, ServerNetworkObserverInterface, GameObserver {
    private final String gameName;
    private final ArrayList<ClientHandler> clientHandlers;
    private final Lobby lobby;
    private final Game game;
    private final List<Request> requests;
    private boolean running;
    private final GameControllerRequestExecutor gameControllerRequestExecutor;
    private final ServerMessageGenerator messageGenerator;

    public GameController(Lobby lobby, int numberOfPlayers, String gameName) {
        this.gameName = gameName;
        this.clientHandlers = new ArrayList<>();
        this.lobby = lobby;
        this.requests = Collections.synchronizedList(new ArrayList<>());
        this.running = true;
        this.game = new Game(numberOfPlayers,this);
        this.messageGenerator = new ServerMessageGenerator(game);
        this.gameControllerRequestExecutor = new GameControllerRequestExecutor(this, messageGenerator);

        System.out.println(gameName + " is ready");
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
    public void submitNewRequest(Request request) {
        requests.addLast(request);
    }

    public Game getGame() {
        return game;
    }

    /**
     * adds the player to the arraylist of players
     * @param client who joined the current game
     */
    public synchronized void enterGame (ClientHandler client){
        clientHandlers.add(client);
        client.setGame(this);
        client.setInGame(true);
        game.players.put(client.getUsername(), new Player(game));
        System.out.println("player " + client.getUsername() + " joined the game");
    }

    /**
     * removes a player from the current game and sends him to the lobby
     * @param client who left the game
     */
    public synchronized void leaveGame (ClientHandler client){
        clientHandlers.remove(client);
        game.reinsertToken(getCurrentPlayer(client).getToken());
        game.players.remove(client.getUsername());
        client.setGame(null);
        client.setInGame(false);
        System.out.println("player " + client.getUsername() + " left the game");
        lobby.enterLobby(client);
        notifyClientConnectedCountChanged();
    }

    public Player getCurrentPlayer(ClientHandler player) {
        return game.players.get(player.getUsername());
    }

    private boolean isNotTheTurnOf(ClientHandler client) {
        return game.turnCounter != clientHandlers.indexOf(client);
    }

    private void passTurn (ClientHandler client) {
        if(game.turnCounter == game.numberOfPlayers-1) game.turnCounter = 0;
        else game.turnCounter++;
        getCurrentPlayer(client).clearTurnState();
    }

    public String getTurnPlayerUsername() {
        return clientHandlers.get(game.turnCounter).getUsername();
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
        //shuffle i client handlers per sciegliere l'ordine del turno
        Collections.shuffle(clientHandlers);
        for (ClientHandler client : clientHandlers) {
            client.send(messageGenerator.startGameMessage(this, getCurrentPlayer(client)));
            getCurrentPlayer(client).clearTurnState();
        }
        game.turnCounter = 0;
    }

    public void place (ClientHandler client,int placeableCardId, boolean facingUp, int x, int y) throws CannotPlaceCardException {
        if(isNotTheTurnOf(client)) {
            throw new CannotPlaceCardException("You can't place a card, it's not your turn!");
        }
        try {
            getCurrentPlayer(client).place(placeableCardId, facingUp, x, y);
        } catch (CardNotInHandException e) {
            throw new CannotPlaceCardException("The card is not in your hand"); //should never happen
        }
    }

    public void directDrawResourceCard (ClientHandler client) throws NotYourTurnException, EmptyDeckException, FullHandException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.directDraw();
        getCurrentPlayer(client).addToHand(cardTemp);
        passTurn(client);
    }

    public void directDrawGoldCard (ClientHandler client) throws EmptyDeckException, FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        GoldCard cardTemp = (GoldCard) game.goldCardDeck.directDraw();
        getCurrentPlayer(client).addToHand(cardTemp);
        passTurn(client);
    }

    public void drawLeftRevealedResourceCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.drawLeftRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        passTurn(client);
    }

    public void drawRightRevealedResourceCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.drawRightRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        passTurn(client);
    }

    public void drawLeftRevealedGoldCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        GoldCard cardTemp = (GoldCard) game.goldCardDeck.drawLeftRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        passTurn(client);
    }

    public void drawRightRevealedGoldCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        GoldCard cardTemp = (GoldCard) game.goldCardDeck.drawRightRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        passTurn(client);
    }

    /**
     * selects the orientation of the starter card and places it
     * @param player player that selected the orientation
     * @param starterCardId starter card id
     * @param facingUp orientation: true if the front is facing up
     */
    public void chooseStarterCardOrientations (ClientHandler player,int starterCardId, boolean facingUp) {
        Player currentPlayer = getCurrentPlayer(player);
        if (currentPlayer.getStarterCard().getId() == starterCardId) {
            currentPlayer.place(currentPlayer.getStarterCard(), facingUp);
            System.out.println("player " + player.getUsername() + "chose facingup = " + facingUp);
            currentPlayer.setStarterCardOrientationSelected(true);
        }
    }

    /**
     * selects the chosen secret objective between the two drawn objective cards
     * @param player player choosing the secret objective
     * @param objectiveCardId chosen secret objective card id
     */
    public void chooseSecretObjectiveCard (ClientHandler player,int objectiveCardId){
        Player currentPlayer = getCurrentPlayer(player);
        for(ObjectiveCard drawnObjectiveCard : currentPlayer.getDrawnObjectiveCards())
            if (drawnObjectiveCard.getId() == objectiveCardId) {
                currentPlayer.setSecretObjective(drawnObjectiveCard);
                System.out.println("player " + player.getUsername() + "chose objective card " + objectiveCardId);
            }
    }

    public void broadcast (JSONObject message){
        for (ClientHandler player : clientHandlers) {
            player.send(message);
        }
    }

    @Override
    public void notifyClientConnectedCountChanged() {
        if(clientHandlers.isEmpty()) {
            System.out.println("there are no more players in the game " + game + ": game is closed");
            lobby.closeGame(gameName);
            running = false;
        }
    }



    @Override
    public void notifyConnectionLoss (ClientHandler clientHandler) {
        System.out.println("player " + clientHandler.getUsername() + " disconnected");
        leaveGame(clientHandler);
        lobby.notifyConnectionLoss(clientHandler);
        if(!(game.getGameState() == GameState.endGame || game.getGameState() == GameState.waitingForPlayers)) {
            game.setGameState(GameState.aClientDisconnected);
            System.out.println("the game is closing");
            broadcast(messageGenerator.closingGameMessage());
            while (!clientHandlers.isEmpty()) {
                leaveGame(clientHandlers.getFirst());
            }
        }
    }

    private boolean gameIsFull() {
        return game.players.size() == game.numberOfPlayers;
    }

    @Override
    public void notifyReady() {
        if(!gameIsFull()) return;
        if(game.getGameState()!=GameState.waitingForPlayers) {
            return;
        }
        for (Player p : game.getPlayers()) {
            if(!p.isReady())
                return;
        }
        lobby.makeUnavailable(gameName);
        game.setGameState(GameState.waitingForCardsSelection);
        gamePreparation();
        sendCardsSelectionMessageToThePlayers();
        System.out.println("all players are ready");
    }

    private void sendCardsSelectionMessageToThePlayers() {
        for (ClientHandler c : clientHandlers) {
            StarterCard starterCard = getCurrentPlayer(c).getStarterCard();
            ObjectiveCard objectiveCard1 = getCurrentPlayer(c).getDrawnObjectiveCards()[0];
            ObjectiveCard objectiveCard2 = getCurrentPlayer(c).getDrawnObjectiveCards()[1];
            c.send(messageGenerator.cardsSelectionMessage(starterCard, objectiveCard1, objectiveCard2));
        }
    }

    @Override
    public void notifyStarterCardAndSecretObjectiveSelected() {
        for (ClientHandler player : clientHandlers) {
            Player currentPlayer = getCurrentPlayer(player);
            if(!currentPlayer.isStarterCardOrientationSelected() || currentPlayer.getSecretObjective() == null) return;
        }
        game.setGameState(GameState.playing);
        startGame();
        System.out.println("game started");
    }

    @Override
    public void notifyLastRound() {

    }

    @Override
    public void notifyEndGame() {

    }

    /**
     * this method invokes the calculateFinalScore method set in the model of each player
     */
    public void calculateFinalScore() {
        ArrayList<Player> classifiedPlayers = new ArrayList<>();
        // aggiungo giocatori alla lista e calcolo punteggi finali
        for (Player p : game.getPlayers()) {
            classifiedPlayers.add(p);
            p.calculateFinalScore();
        }

        classifiedPlayers.sort((p1, p2) -> {
            // Ordina per score
            int compare = p1.compareTo(p2.getScore());
            // Se lo score è lo stesso, ordina per obiettivi completati
            if (compare == 0) {
                return p1.compareTo(p2.getNumOfCompletedObjectiveCards());
            }
            return compare;
        });
        //TODO ritornare l arraylist finale.
    }

}



//    private void broadcast(JSONObject message, ClientHandler  disconnectedPlayer) {
//        for (ClientHandler player : clients) {
//            if(player == disconnectedPlayer) continue;
//            player.send(message);
//        }
//    }

