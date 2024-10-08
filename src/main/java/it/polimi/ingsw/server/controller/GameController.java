package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.network.ServerNetworkObserver;
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
import static it.polimi.ingsw.util.supportclasses.Constants.SCORE_GOAL;

/**
 * This class manages the logic of a game instance. It acts as the central controller
 *  for the game, coordinating interactions between players, the game model and the server network.
 */
public class GameController implements Runnable, ServerNetworkObserver, GameObserver {
    private final String gameName;
    private final ArrayList<ClientHandler> clientHandlers;
    private final Lobby lobby;
    private final Game game;
    private final List<Request> requests;
    private boolean echo;
    private boolean running;
    private final GameRequestHandler gameRequestHandler;
    private final ServerMessageGenerator messageGenerator;

    public GameController(Lobby lobby, int numberOfPlayers, String gameName, boolean echo) {
        this.gameName = gameName;
        this.clientHandlers = new ArrayList<>();
        this.lobby = lobby;
        this.requests = Collections.synchronizedList(new ArrayList<>());
        this.echo = echo;
        running = true;
        this.game = new Game(numberOfPlayers,this);
        this.messageGenerator = new ServerMessageGenerator(game);
        this.gameRequestHandler = new GameRequestHandler(this, messageGenerator, game);

        if (echo) {
            System.out.println("Game '" + gameName + "' is ready to receive players");
        }
    }

    @Override
    public void run() {
        while (running) {
            while (!requests.isEmpty()) {
                gameRequestHandler.execute(requests.getFirst());
                requests.removeFirst();
            }
        }
    }

    public void echoOff() {
        echo = false;
    }

    public void echoOn() {
        echo = true;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public void submitNewRequest(Request request) {
        requests.addLast(request);
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public int getNumberOfPlayers() {
        return game.getNumberOfPlayers();
    }

    /**
     * Adds the player to the arraylist of players.
     * @param client Client who joined the current game.
     */
    public synchronized void enterGame (ClientHandler client) throws GameIsFullException {
        if(gameIsFull()) {
            throw new GameIsFullException();
        }
        clientHandlers.add(client);
        client.setGame(this);
        client.setInGame(true);
        game.getPlayersHashMap().put(client.getUsername(), new Player(game));
        if (echo) {
            System.out.println("Player '" + client.getUsername() + "' joined the game '" + gameName +"'");
        }
    }

    /**
     * Removes a player from the current game and sends him to the lobby.
     * @param client Client who left the game.
     */
    public synchronized void leaveGame (ClientHandler client){
        clientHandlers.remove(client);
        game.reinsertToken(getCurrentPlayer(client).getToken());
        game.getPlayersHashMap().remove(client.getUsername());
        client.setGame(null);
        client.setInGame(false);
        if (echo) {
            System.out.println("Player '" + client.getUsername() + "' left the game '" + gameName +"'");
        }
        lobby.enterLobby(client);
        if(game.getGameState() == GameState.waitingForCardsSelection || game.getGameState() == GameState.playing || game.getGameState() == GameState.lastRound) {
            disconnectionDuringGameProcedure();
        }
        notifyConnectedClientCountChanged();
    }

    /**
     * Retrieves the Player object associated with the provided ClientHandler.
     * @param client The ClientHandler representing the player.
     * @return The Player object for the given client, or null if not found.
     */
    public Player getCurrentPlayer(ClientHandler client) {
        return game.getPlayersHashMap().get(client.getUsername());
    }

    /**
     * Sets the game state to aClientDisconnected, broadcasts a closing game message to all players,
     *  and then removes all remaining players from the game.
     */
    private void disconnectionDuringGameProcedure() {
        game.setGameState(GameState.aClientDisconnected);
        if (echo) {
            System.out.println("Game '" + gameName + "' is closing");
        }
        broadcast(messageGenerator.closingGameMessage());

    }

    /**
     * Compares the game's turn counter with the index of the client handler in the clientHandlers list.
     * @param client The ClientHandler representing the player.
     * @return true if it's not the player's turn, false otherwise.
     */
    private boolean isNotTheTurnOf(ClientHandler client) {
        return game.getTurnCounter() != clientHandlers.indexOf(client);
    }

    /**
     * Increments the game's turn counter, resets the current player's turn state,
     * broadcasts a turn update message to all players, and checks for the end game condition.
     * @param client The ClientHandler representing the current player.
     */
    private void passTurn (ClientHandler client) {
        if(game.getTurnCounter() == game.getNumberOfPlayers() -1) game.setTurnCounter(0);
        else game.setTurnCounter(game.getTurnCounter() + 1);
        getCurrentPlayer(client).clearTurnState();
        broadcast(messageGenerator.turnPlayerUpdateMessage(this));
        notifyEndGame();
    }

    /**
     * Retrieves the ClientHandler object at the index of the game's turn counter from the clientHandlers list.
     * @return The username of the player whose turn it is
     */
    public String getTurnPlayerUsername() {
        return clientHandlers.get(game.getTurnCounter()).getUsername();
    }

    /**
     * Draws a starter card for each player.
     */
    private void starterCardsSelectionPreparation() {
        StarterCard starterCard=null;
        for(Player p : game.getPlayers()) {
            try {
                starterCard = (StarterCard) game.getStarterCardDeck().directDraw() ;
            } catch (EmptyDeckException ignored) {}
            p.setStarterCard(starterCard);
        }
    }

    /**
     * Draws 2 objective cards for each player.
     */
    private void secretObjectiveCardsSelectionPreparation()
    {
        for (Player p : game.getPlayers()) {
            try {
                ObjectiveCard cardTemp1 = ((ObjectiveCard) game.getObjectiveCardDeck().directDraw());
                ObjectiveCard cardTemp2 = ((ObjectiveCard) game.getObjectiveCardDeck().directDraw());
                p.setDrawnObjectiveCards(new ObjectiveCard[]{cardTemp1, cardTemp2});
            } catch (EmptyDeckException ignored) {
            }
        }
    }

    /**
     * Prepares the game to wait for players to choose the starter card orientation and the secret objective.
     */
    private void gamePreparation (){
        starterCardsSelectionPreparation();
        secretObjectiveCardsSelectionPreparation();
    }

    /**
     * Sets the flag ready to true when the player is ready to play.
     * @param player The player that is now ready.
     */
    public synchronized void ready(ClientHandler player){
        getCurrentPlayer(player).setReady(true);
        if(echo) System.out.println("In game '" + gameName + "' player '" + player.getUsername() + "' is ready");
    }

    /**
     * Communicates to the players the game is about to start and sends their cards.
     */
    public void startGame () {
        //shuffles the client handlers to decide the round player sequence randomly
        Collections.shuffle(clientHandlers);
        //initializes the hand of each player
        for(Player p : game.getPlayers()) {
            p.initializeHand();
        }
        //sends the starting messages to each player
        for (ClientHandler client : clientHandlers) {
            client.send(messageGenerator.startGameMessage(this, getCurrentPlayer(client)));
            getCurrentPlayer(client).clearTurnState();
        }
        broadcast(messageGenerator.updatedScoresMessage(this));
        game.setTurnCounter(0);
        if(echo) {
            System.out.println("Game '" + gameName + "' is starting");
        }
    }

    /**
     * Places a card on the board for the player associated with the provided ClientHandler.
     * @param client The ClientHandler representing the player who wants to place the card.
     * @param placeableCardId The ID of the card in the player's hand to be placed.
     * @param facingUp Whether the card should be placed face up (true) or face down (false).
     * @param x The x-coordinate on the board where the card should be placed.
     * @param y The y-coordinate on the board where the card should be placed.
     * @throws CannotPlaceCardException Thrown if it's not the player's turn or the card cannot be placed for some reason.
     */
    public void place (ClientHandler client,int placeableCardId, boolean facingUp, int x, int y) throws CannotPlaceCardException {
        if(isNotTheTurnOf(client)) {
            throw new CannotPlaceCardException("You can't place a card, it's not your turn!");
        }
        try {
            getCurrentPlayer(client).place(placeableCardId, facingUp, x, y);
        } catch (CardNotInHandException e) {
            throw new CannotPlaceCardException("The card is not in your hand"); //should never happen
        }
        broadcast(messageGenerator.updatedScoresMessage(this));
        if(echo) System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' placed the card " + placeableCardId + " at X:" + x + " Y:" + y);
        if(game.getGameState() == GameState.lastRound) passTurn(client);
    }

    /**
     * Allows the player associated with the ClientHandler to directly draw a resource card from the deck.
     * @param client The ClientHandler representing the player who wants to draw a card.
     * @throws NotYourTurnException Thrown if it's not the player's turn.
     * @throws EmptyDeckException Thrown if the resource card deck is empty.
     * @throws FullHandException Thrown if the player's hand is already full.
     * @throws CannotDrawException Thrown if the player hasn't placed a card yet this turn.
     */
    public void directDrawResourceCard (ClientHandler client) throws NotYourTurnException, EmptyDeckException, FullHandException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        ResourceCard cardTemp = (ResourceCard) game.getResourceCardDeck().directDraw();
        getCurrentPlayer(client).addToHand(cardTemp);
        if(echo) System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' has drawn a resource card from the deck");
        notifyLastRound();
        passTurn(client);
    }

    /**
     * Allows the player associated with the ClientHandler to directly draw a gold card from the deck.
     * @param client The ClientHandler representing the player who wants to draw a card.
     * @throws NotYourTurnException Thrown if it's not the player's turn.
     * @throws FullHandException Thrown if the player's hand is already full.
     * @throws CannotDrawException Thrown if the player hasn't placed a card yet this turn.
     */
    public void directDrawGoldCard (ClientHandler client) throws EmptyDeckException, FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        GoldCard cardTemp = (GoldCard) game.getGoldCardDeck().directDraw();
        getCurrentPlayer(client).addToHand(cardTemp);
        if(echo) System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' has drawn a gold card from the deck");
        notifyLastRound();
        passTurn(client);
    }

    /**
     * Allows the player associated with the ClientHandler to draw a revealed resource card from the left side of the deck.
     * @param client The ClientHandler representing the player who wants to draw a card.
     * @throws NotYourTurnException Thrown if it's not the player's turn.
     * @throws FullHandException Thrown if the player's hand is already full.
     * @throws CannotDrawException Thrown if the player hasn't placed a card yet this turn.
     */
    public void drawLeftRevealedResourceCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        ResourceCard cardTemp = (ResourceCard) game.getResourceCardDeck().drawLeftRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        if(echo) System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' has drawn the left revealed resource card");
        notifyLastRound();
        passTurn(client);
    }

    /**
     * Allows the player associated with the ClientHandler to draw a revealed resource card from the right side of the deck.
     * @param client The ClientHandler representing the player who wants to draw a card.
     * @throws NotYourTurnException Thrown if it's not the player's turn.
     * @throws FullHandException Thrown if the player's hand is already full.
     * @throws CannotDrawException Thrown if the player hasn't placed a card yet this turn.
     */
    public void drawRightRevealedResourceCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        ResourceCard cardTemp = (ResourceCard) game.getResourceCardDeck().drawRightRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        if(echo) System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' has drawn the right revealed resource card");
        notifyLastRound();
        passTurn(client);
    }

    /**
     * Allows the player associated with the ClientHandler to draw a revealed gold card from the left side of the deck.
     * @param client The ClientHandler representing the player who wants to draw a card.
     * @throws NotYourTurnException Thrown if it's not the player's turn.
     * @throws FullHandException Thrown if the player's hand is already full.
     * @throws CannotDrawException Thrown if the player hasn't placed a card yet this turn.
     */
    public void drawLeftRevealedGoldCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        GoldCard cardTemp = (GoldCard) game.getGoldCardDeck().drawLeftRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        if(echo) System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' has drawn the left revealed gold card");
        notifyLastRound();
        passTurn(client);
    }

    /**
     * Allows the player associated with the ClientHandler to draw a revealed gold card from the right side of the deck.
     * @param client The ClientHandler representing the player who wants to draw a card.
     * @throws NotYourTurnException Thrown if it's not the player's turn.
     * @throws FullHandException Thrown if the player's hand is already full.
     * @throws CannotDrawException Thrown if the player hasn't placed a card yet this turn.
     */
    public void drawRightRevealedGoldCard (ClientHandler client) throws FullHandException, NotYourTurnException, CannotDrawException {
        if (isNotTheTurnOf(client)) {
            throw new NotYourTurnException();
        }
        if (!getCurrentPlayer(client).hasAlreadyPlaced()) {
            throw new CannotDrawException();
        }

        GoldCard cardTemp = (GoldCard) game.getGoldCardDeck().drawRightRevealedCard();
        getCurrentPlayer(client).addToHand(cardTemp);
        if(echo) System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' has drawn the right revealed gold card");
        notifyLastRound();
        passTurn(client);
    }

    /**
     * Selects the orientation of the starter card and places it.
     * @param client The player that selected the orientation.
     * @param starterCardId The starter card id.
     * @param facingUp The orientation: true if the front is facing up, false otherwise.
     */
    public void chooseStarterCardSide(ClientHandler client, int starterCardId, boolean facingUp) {
        Player currentPlayer = getCurrentPlayer(client);
        if (currentPlayer.getStarterCard().getId() == starterCardId) {
            currentPlayer.place(currentPlayer.getStarterCard(), facingUp);
            if (echo) {
                String side;
                if(facingUp) side = "front";
                else side = "back";
                System.out.println("In game '"+ gameName + "' player '" + client.getUsername() + "' chose to play their starter card on the " + side);
            }
            currentPlayer.setStarterCardOrientationSelected(true);
        }
    }

    /**
     * Selects the chosen secret objective between the two drawn objective cards.
     * @param client The player choosing the secret objective.
     * @param objectiveCardId The chosen secret objective card id.
     */
    public void chooseSecretObjectiveCard (ClientHandler client,int objectiveCardId){
        Player currentPlayer = getCurrentPlayer(client);
        for(ObjectiveCard drawnObjectiveCard : currentPlayer.getDrawnObjectiveCards())
            if (drawnObjectiveCard.getId() == objectiveCardId) {
                currentPlayer.setSecretObjective(drawnObjectiveCard);
                if (echo) {
                    System.out.println("In game '"+ gameName + "' player '" + client.getUsername() + "' chose to the secret objective " + objectiveCardId);
                }
            }
    }

    /**
     * Broadcasts a JSON message to all connected clients in the game.
     * @param message The JSON message to be sent to all clients.
     */
    public void broadcast (JSONObject message){
        for (ClientHandler player : clientHandlers) {
            player.send(message);
        }
    }

    @Override
    public void notifyConnectedClientCountChanged() {
        if(clientHandlers.isEmpty()) {
            if (echo) {
                System.out.println("There are no more players in the game '" + gameName + "': game is closed");
            }
            lobby.closeGame(gameName);
            running = false;
        }
    }

    @Override
    public void notifyConnectionLoss (ClientHandler client) {
        if (echo) {
            System.out.println("In game '" + gameName + "' player '" + client.getUsername() + "' disconnected");
        }
        leaveGame(client);
        lobby.notifyConnectionLoss(client);
        if(!(game.getGameState() == GameState.endGame || game.getGameState() == GameState.waitingForPlayers)) {
            disconnectionDuringGameProcedure();
        }
    }

    private boolean gameIsFull() {
        return game.getPlayersHashMap().size() == game.getNumberOfPlayers();
    }

    @Override
    public void notifyReady() {
        if(!gameIsFull()) return;
        if(game.getGameState()!=GameState.waitingForPlayers) return;

        for (Player p : game.getPlayers()) {
            if(!p.isReady())
                return;
        }
        lobby.makeUnavailable(gameName);
        game.setGameState(GameState.waitingForCardsSelection);
        gamePreparation();
        sendCardsSelectionMessageToThePlayers();
        if (echo) {
            System.out.println("In game '" + gameName + "' all players are ready");
        }
    }

    /**
     * sends a message to each player containing their starter card and drawn objective cards.
     */
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
        if(game.getGameState() != GameState.waitingForCardsSelection) return;
        for (ClientHandler player : clientHandlers) {
            Player currentPlayer = getCurrentPlayer(player);
            if(!currentPlayer.isStarterCardOrientationSelected() || currentPlayer.getSecretObjective() == null) return;
        }
        game.setGameState(GameState.playing);
        if(echo) {
            System.out.println("In game '" + gameName + "' all players selected the starter card side and secret objective");
        }
        startGame();
    }

    @Override
    public void notifyLastRound() {
        if(game.getGameState() != GameState.playing) return;
        String reason;
        for (ClientHandler clientHandler : clientHandlers) {
            if (getCurrentPlayer(clientHandler).getScore() >= SCORE_GOAL) {
                game.setGameState(GameState.lastRound);
                reason = "player " + clientHandler.getUsername() + " has 20 or more points";
                broadcast(messageGenerator.lastRoundMessage(reason));
                if(echo) {
                    System.out.println("Game '" + gameName + "' is at the last round because " + reason);
                }
                return;
            }
        }
        
        if (game.getGoldCardDeck().isEmpty() && game.getResourceCardDeck().isEmpty()) {
            game.setGameState(GameState.lastRound);
            reason = "decks are empty";
            broadcast(messageGenerator.lastRoundMessage(reason));
            if(echo) {
                System.out.println("Game '" + gameName + "' is at the last round because " + reason);
            }
        }

    }

    @Override
    public void notifyEndGame() {
        if(game.getGameState()==GameState.endGame) return;
        if(game.getGameState()!=GameState.lastRound) return;
        if(game.getTurnCounter() != 0) return;
        game.setGameState(GameState.endGame);
        if(echo) System.out.println("Game '" + gameName + "' has ended");
        calculateFinalScore();
    }

    /**
     * Calculates the final leaderboard and sends it to each client.
     */
    public void calculateFinalScore() {
        ArrayList<ClientHandler> classifiedPlayers = new ArrayList<>();
        for (ClientHandler c : clientHandlers) {
            classifiedPlayers.add(c);
            getCurrentPlayer(c).calculateFinalScore();
        }
        sortPlayers(classifiedPlayers);
        broadcast(messageGenerator.leaderBoardMessage(this , classifiedPlayers));
        if(echo) {
            System.out.println("Game '" + gameName + "' leaderboard:");
            for (int i = 0; i < classifiedPlayers.size(); i++) {
                System.out.println(i + ": " + classifiedPlayers.get(i).getUsername() + " " + getCurrentPlayer(classifiedPlayers.get(i)).getScore() + " points (" + getCurrentPlayer(classifiedPlayers.get(i)).getNumOfCompletedObjectiveCards() + " objectives completed)");
            }
        }
    }

    private void sortPlayers(ArrayList<ClientHandler> clients) {
        clients.sort((c1, c2) -> getCurrentPlayer(c1).compareTo(getCurrentPlayer(c2)));
    }

}
