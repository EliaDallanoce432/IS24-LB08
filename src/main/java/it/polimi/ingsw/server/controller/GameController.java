package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.network.ServerNetworkObserverInterface;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Color;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameController implements Runnable, ServerNetworkObserverInterface {
    private String gameName;
    private final int numberOfExpectedPlayers;
    private final ArrayList<ClientHandler> clients;
    private final Lobby lobby;
    private Game game;
    ArrayList<StarterCard> drawnStarterCards;
    ArrayList<ObjectiveCard> drawnObjectiveCards;
    private int turn;
    private List<Request> requests;
    private boolean running;
    private GameControllerRequestExecutor gameControllerRequestExecutor;

    public GameController(Lobby lobby, int numberOfExpectedPlayers, String gameName) {
        this.clients = new ArrayList<>();
        this.numberOfExpectedPlayers = numberOfExpectedPlayers;
        this.lobby = lobby;
        this.drawnStarterCards = new ArrayList<>();
        this.drawnObjectiveCards = new ArrayList<>();
        this.requests = Collections.synchronizedList(new ArrayList<Request>());
        this.running = true;
        this.gameControllerRequestExecutor = new GameControllerRequestExecutor(this);
        this.game = new Game(numberOfExpectedPlayers);
        System.out.println(gameName + " is ready");
    }

    public String getGameName() {
        return gameName;
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

    /*
        private void waitForEveryoneToJoinAndBeReady() throws InterruptedException {
            while (clients.size() < numberOfExpectedPlayers || !allPlayersReady()) {
                if (clients.isEmpty()) {
                    //throw new GameException("No players joined the game.");
                }
                System.out.println("Waiting for players to join and be ready...");
                Thread.sleep(5000);
            }
        }

        private boolean allPlayersReady() {
            for (ClientHandler player : clients) {
                if (!player.isReady()) {
                    return false;
                }
            }
            return true;
        }
    */
        private void gamePreparation (){
            List<Color> colors = Arrays.asList(Color.blue, Color.yellow, Color.red, Color.green);
            Collections.shuffle(colors); //randomize color token player
            Collections.shuffle(clients); //randomize first player
            for (int i = 0; i < numberOfExpectedPlayers; i++) {
                game.players[i] = new Player(clients.get(i).getUsername(), colors.get(i));
            }
            for (int i = 0; i < numberOfExpectedPlayers; i++) {
                try {
                    drawnStarterCards.add((StarterCard) game.starterCardDeck.directDraw());
                    clients.get(i).send(ServerMessageGenerator.generateDrawnStarterCardMessage(drawnStarterCards.get(i)), true);
                } catch (EmptyDeckException ignored) {
                }
            }
            System.out.println("aspetto che tutti scelgano una carta starter...");
            System.out.println(drawnStarterCards);
            while (drawnStarterCards.size()>0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(drawnStarterCards);
                //wait for every player to choose starter card orientation
            }
            for (int i = 0; i < numberOfExpectedPlayers; i++) {
                try {
                    ObjectiveCard cardtemp1 = ((ObjectiveCard) game.objectiveCardDeck.directDraw());
                    ObjectiveCard cardtemp2 = ((ObjectiveCard) game.objectiveCardDeck.directDraw());
                    drawnObjectiveCards.add(cardtemp1);
                    drawnObjectiveCards.add(cardtemp2);
                    JSONObject response = clients.get(i).send(ServerMessageGenerator.generateDrawnObjectiveCardsMessage(cardtemp1, cardtemp2), true);
                    System.out.println("sending ids: " + cardtemp1.getId() + " - " + cardtemp2.getId() + "to: " + clients.get(i).getUsername());
                    clients.get(i).send(ServerMessageGenerator.generateDrawnObjectiveCardsMessage(cardtemp1, cardtemp2), true);

                } catch (EmptyDeckException ignored) {
                }
            }
            while (drawnObjectiveCards.size() > numberOfExpectedPlayers) {

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(drawnObjectiveCards);
                //wait for every player to choose starter card orientation
            }
            System.out.println("tutti i giocatori hanno scelto una objective card");

            for (int i = 0; i < clients.size(); i++) {
                try {
                    game.players[i].addToHand((PlaceableCard) game.resourceCardDeck.directDraw());
                    game.players[i].addToHand((PlaceableCard) game.resourceCardDeck.directDraw());
                    game.players[i].addToHand((PlaceableCard) game.goldCardDeck.directDraw());
                } catch (FullHandException | EmptyDeckException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    /**
     * sets the flag ready to true when the player is ready to play
     * @param clientHandler
     */
    public void ready (ClientHandler clientHandler){
            clientHandler.setReady(true);
        }

        /**
         * checks if one player reached 20 point, at the end of the round
         * @return boolean
         */
        public boolean checkWinner () {
            for (int i = 0; i < clients.size(); i++) {
                if (game.players[i].getScore() >= 20)
                    return true;
            }
            return false;
        }

        /**
         * communicates to players the game is about to start and sends their cards
         */
        public void startGame () {
            for (int i = 0; i < numberOfExpectedPlayers; i++) {
                clients.get(i).send(ServerMessageGenerator.generateStartGameMessage(), true);
                //TODO togliere i commenti
//                clients.get(i).send(ServerMessageGenerator.generateUpdateHandMessage(game.players[i].getHand()), true);
//                clients.get(i).send(ServerMessageGenerator.generateDrawableCardsMessage(game.resourceCardDeck.getDrawableCardsId(), game.goldCardDeck.getDrawableCardsId()), true);
                this.sendUpDatedScores();
            }
            for (ClientHandler player : clients)
                player.clearTurnState();
        }

        /**
         * sends the updated scores to every player
         */
        public void sendUpDatedScores () {
            ArrayList<Integer> scores = new ArrayList<>();
            for (int i = 0; i < numberOfExpectedPlayers; i++) {
                scores.add(game.players[i].getScore());
            }

            ArrayList<String> names = new ArrayList<>();
            for (int i = 0; i < numberOfExpectedPlayers; i++) {
                names.add(game.players[i].getUsername());
            }

            for (int i = 0; i < numberOfExpectedPlayers; i++) {
                clients.get(i).send(ServerMessageGenerator.generateUpdatedScoreMessage(names, scores), true);
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
        public void notifyConnectionLoss (ClientHandler clientHandler){
            Object lock = new Object();
            synchronized (lock) {
                leaveGame(clientHandler);
            }
            lobby.notifyConnectionLoss(clientHandler);
            synchronized (lock) {
                broadcast(ServerMessageGenerator.closingGameMessage());
            }
            while (!clients.isEmpty()) {
            } //waits for everyone to leave back to the lobby
            Thread.currentThread().interrupt();
        }

    /**
     * adds the player to the arraylist of players
     * @param player who joined the current game
     */
    public synchronized void enterGame (ClientHandler player){
            clients.add(player);
            player.setGame(this);
            player.setInGame(true);
        }

    /**
     * removes a player from the current game and sends him to the lobby
     * @param player who left the game
     */
    public synchronized void leaveGame (ClientHandler player){
            clients.remove(player);
            player.setGame(null);
            player.setInGame(false);
            lobby.enterLobby(player);
        }
        public synchronized void place (ClientHandler player,int placeableCardId, boolean facingUp, int x, int y) throws
        CannotPlaceCardException, NotYourTurnException {
            if(turn != clients.indexOf(player)) {
                throw new NotYourTurnException();
            }
            PlaceableCard cardInHand = null;
            for (int i = 0; i < 3; i++) {
                if (game.players[clients.indexOf(player)].getHand().get(i).getId() == placeableCardId) {
                    cardInHand = game.players[clients.indexOf(player)].getHand().get(i);
                    break;
                }
            }
            game.players[clients.indexOf(player)].place(cardInHand, facingUp, x, y);
            player.setAlreadyPlaced(true);
        }
        public synchronized void place (ClientHandler player,int starterCardId, boolean facingUp){
            StarterCard starterCard = null;
            for (StarterCard drawnStarterCard : drawnStarterCards) {
                if (drawnStarterCard.getId() == starterCardId) {
                    starterCard = drawnStarterCard;
                    break;
                }
            }
            game.players[clients.indexOf(player)].place(starterCard, facingUp);
        }
        public synchronized void directDrawResourceCard (ClientHandler player) throws NotYourTurnException,
                EmptyDeckException, FullHandException, CannotDrawException {

            if (turn != clients.indexOf(player)) {
                throw new NotYourTurnException();
            }

            if (!player.hasAlreadyPlaced()) {
                throw new CannotDrawException();
            }
            ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.directDraw();
            game.players[clients.indexOf(player)].addToHand(cardTemp);
            passTurn(player);
        }
        public synchronized void directDrawGoldCard (ClientHandler player) throws EmptyDeckException, FullHandException, NotYourTurnException, CannotDrawException {

            if (turn != clients.indexOf(player)) {
                throw new NotYourTurnException();
            }
            if (!player.hasAlreadyPlaced()) {
                throw new CannotDrawException();
            }

            GoldCard cardTemp = (GoldCard) game.goldCardDeck.directDraw();
            game.players[clients.indexOf(player)].addToHand(cardTemp);
            passTurn(player);
        }
        public synchronized void drawLeftRevealedResourceCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {

            if (turn != clients.indexOf(player)) {
                throw new NotYourTurnException();
            }

            if (!player.hasAlreadyPlaced()) {
                throw new CannotDrawException();
            }
            ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getLeftRevealedCard();
            game.players[clients.indexOf(player)].addToHand(cardTemp);
            passTurn(player);
        }
        public synchronized void drawRightRevealedResourceCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {
            if (turn != clients.indexOf(player)) {
                throw new NotYourTurnException();
            }
            if (!player.hasAlreadyPlaced()) {
                throw new CannotDrawException();
            }

            ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getRightRevealedCard();
            game.players[clients.indexOf(player)].addToHand(cardTemp);
            passTurn(player);
        }
        public synchronized void drawLeftRevealedGoldCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {

            if (turn != clients.indexOf(player)) {
                throw new NotYourTurnException();
            }
            if (!player.hasAlreadyPlaced()) {
                throw new CannotDrawException();
            }
            GoldCard cardTemp = (GoldCard) game.goldCardDeck.getLeftRevealedCard();
            game.players[clients.indexOf(player)].addToHand(cardTemp);
            passTurn(player);
        }
        public synchronized void drawRightRevealedGoldCard (ClientHandler player) throws FullHandException, NotYourTurnException, CannotDrawException {

            if (turn != clients.indexOf(player)) {
                throw new NotYourTurnException();
            }

            if (!player.hasAlreadyPlaced()) {
                throw new CannotDrawException();
            }
            GoldCard cardTemp = (GoldCard) game.goldCardDeck.getRightRevealedCard();
            game.players[clients.indexOf(player)].addToHand(cardTemp);
            passTurn(player);
        }
        public void chooseStarterCardOrientations (ClientHandler player,int starterCardId, boolean facingUp)
        {
            for (StarterCard card : drawnStarterCards) {
                if (card.getId() == starterCardId) {
                    game.players[clients.indexOf(player)].place(drawnStarterCards.remove(drawnStarterCards.indexOf(card)), facingUp);
                    System.out.println("il player " + player.getUsername() + "ha scelto facingup = " + facingUp);
                    System.out.println(drawnStarterCards);
                    System.out.println("is empty: " + drawnStarterCards.isEmpty() + "size: " + drawnStarterCards.size());
                    return;
                }
            }
        }
        public synchronized void chooseSecretObjectiveCard (ClientHandler player,int objectiveCardId){
            for (ObjectiveCard card : drawnObjectiveCards) {
                if (card.getId() == objectiveCardId) {
                    game.players[clients.indexOf(player)].setSecretObjective(drawnObjectiveCards.remove(drawnObjectiveCards.indexOf(card)));
                    System.out.println("il player " + player.getUsername() + "ha scelto la objective card " + objectiveCardId);
                    System.out.println(drawnObjectiveCards);
                    return;
                }
            }
        }

        private void passTurn (ClientHandler player){
            turn = (turn + 1) % clients.size();
            player.clearTurnState();
        }

        private void broadcast (JSONObject message){
            for (ClientHandler player : clients) {
                player.send(message, true);
            }
        }
    }
    //TODO calcolare punteggio finale
//    private void broadcast(JSONObject message, ClientHandler  disconnectedPlayer) {
//        for (ClientHandler player : clients) {
//            if(player == disconnectedPlayer) continue;
//            player.send(message);
//        }
//    }

