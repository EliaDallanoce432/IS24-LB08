package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.Lobby;
import it.polimi.ingsw.network.ServerNetworkObserverInterface;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import it.polimi.ingsw.util.customexceptions.FullHandException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController implements Runnable, ServerNetworkObserverInterface {
    private String gameName;
    private int numberOfExpectedPlayers;
    private ArrayList<ClientHandler> clients;
    private Lobby lobby;
    private Game game;
    ArrayList<StarterCard> drawnStarterCards;
    ArrayList<ObjectiveCard> drawnObjectiveCards;
    public GameController(Lobby lobby, int numberOfPlayers, String gameName) {
        this.clients = new ArrayList<>();
        this.numberOfExpectedPlayers = numberOfPlayers;
        this.lobby = lobby;
        this.drawnStarterCards = new ArrayList<>();
        this.game = new Game(numberOfPlayers);
        System.out.println(gameName + " is ready");
    }

    public String getGameName() {
        return gameName;
    }

    public int getNumberOfPlayers() {
        return numberOfExpectedPlayers;
    }

    public synchronized void enterGame(ClientHandler player) {
        clients.add(player);
        player.setGame(this);
        player.setInGame(true);
    }

    public synchronized void leaveGame(ClientHandler player) {
        clients.remove(player);
        player.setGame(null);
        player.setInGame(false);
        lobby.enterLobby(player);
    }
    public synchronized void  place(ClientHandler player, int placeableCardId, boolean facingUp, int x, int y) throws CannotPlaceCardException {
        PlaceableCard cardInHand = null;
        for (int i = 0; i < 3; i++) {
            if(game.players[clients.indexOf(player)].getHand().get(i).getId()==placeableCardId)
                cardInHand=game.players[clients.indexOf(player)].getHand().get(i);
        }
        game.players[clients.indexOf(player)].place(cardInHand, facingUp, x, y);
    }
    public synchronized void place(ClientHandler player, int starterCardId, boolean facingUp) {
        StarterCard starterCard= null;
        for (StarterCard drawnStarterCard : drawnStarterCards) {
            if (drawnStarterCard.getId() == starterCardId)
                starterCard = drawnStarterCard;
        }
        game.players[clients.indexOf(player)].place(starterCard, facingUp);
    }
    public synchronized void directDrawResourceCard(ClientHandler player) throws EmptyDeckException, FullHandException {
        ResourceCard cardTemp = (ResourceCard)game.resourceCardDeck.directDraw();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public synchronized void directDrawGoldCard(ClientHandler player) throws EmptyDeckException, FullHandException {
        GoldCard cardTemp = (GoldCard)game.goldCardDeck.directDraw();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public synchronized void drawLeftRevealedResourceCard(ClientHandler player) throws FullHandException {
        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getLeftRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public synchronized void drawRightRevealedResourceCard(ClientHandler player) throws FullHandException {
        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getRightRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public synchronized void drawLeftRevealedGoldCard(ClientHandler player) throws FullHandException {
        GoldCard cardTemp = (GoldCard) game.goldCardDeck.getLeftRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public synchronized void drawRightRevealedGoldCard(ClientHandler player) throws FullHandException {
        GoldCard cardTemp = (GoldCard) game.goldCardDeck.getRightRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public synchronized void chooseStarterCardOrientations(ClientHandler player, int starterCardId, boolean facingUp) {
        for (StarterCard card : drawnStarterCards) {
             if (card.getId() == starterCardId) {
                 game.players[clients.indexOf(player)].place(drawnStarterCards.remove(starterCardId),facingUp);
             }
        }
    }
    public synchronized void chooseSecretObjectiveCard(ClientHandler player, int objectiveCardId) {
        for (ObjectiveCard card : drawnObjectiveCards) {
            if (card.getId() == objectiveCardId) {
                game.players[clients.indexOf(player)].setSecretObjective(drawnObjectiveCards.remove(objectiveCardId));
            }
        }
    }
    public void run() {
        waitForEveryoneToJoinAndBeReady();
        gamePreparation();
        startGame();
    }
    private void waitForEveryoneToJoinAndBeReady() {
        int countReadyPlayers = 0;
        while (clients.size() < numberOfExpectedPlayers && countReadyPlayers<numberOfExpectedPlayers) {
            if(clients.isEmpty())
            {//TODO chiudi partita
            }
            countReadyPlayers = 0;
            for (ClientHandler player : clients) {
                if (player.isReady())
                    countReadyPlayers++;
            }
        }
    }

    private void gamePreparation(){
        game = new Game(numberOfExpectedPlayers);
        ArrayList<Color> colors = new ArrayList<>(List.of(Color.blue, Color.yellow, Color.red, Color.green));
        Collections.shuffle(colors); //randomize color token player
        Collections.shuffle(clients); //randomize first player
        for (int i = 0; i < numberOfExpectedPlayers; i++) {
            game.players[i]=new Player(clients.get(i).getUsername(), colors.get(i));
        }
        for (int i = 0; i < numberOfExpectedPlayers; i++) {
            try {
                drawnStarterCards.add((StarterCard) game.starterCardDeck.directDraw());
                clients.get(i).send(ServerMessageGenerator.generateDrawnStarterCardMessage(drawnStarterCards.get(i)));
            } catch (EmptyDeckException ignored) {}
        }
        while(!drawnStarterCards.isEmpty())
        {
            //wait for every player to choose starter card orientation
        }
        for (int i = 0; i < numberOfExpectedPlayers; i++) {
            try {
                ObjectiveCard cardtemp1;
                ObjectiveCard cardtemp2;
                cardtemp1=((ObjectiveCard) game.objectiveCardDeck.directDraw());
                cardtemp2=((ObjectiveCard) game.objectiveCardDeck.directDraw());
                drawnObjectiveCards.add(cardtemp1);
                drawnObjectiveCards.add(cardtemp2);

                JSONObject response = clients.get(i).send(ServerMessageGenerator.generateDrawnObjectiveCardsMessage(cardtemp1, cardtemp2));

            } catch (EmptyDeckException ignored) {}
        }
        while(drawnObjectiveCards.size()>numberOfExpectedPlayers)
        {
            //wait for every player to choose starter card orientation
        }

        for (int i = 0; i< clients.size(); i++) {
            try {
                game.players[i].addToHand((PlaceableCard) game.resourceCardDeck.directDraw());
                game.players[i].addToHand((PlaceableCard) game.resourceCardDeck.directDraw());
                game.players[i].addToHand((PlaceableCard) game.goldCardDeck.directDraw());
            } catch (FullHandException e) {
                throw new RuntimeException(e);
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void ready(ClientHandler clientHandler) { clientHandler.setReady(true);}

    /**
     * check if one player reached 20 point, at the end on the round
     * @return boolean
     */
    public boolean checkWinner() {
        for (int i = 0; i < clients.size(); i++) {
            if(game.players[i].getScore()>=20)
                return true;
        }
        return false;
    }

    /**
     * communicates to players the game is about to start and sends their cards
     */
    public void startGame () {
        for (int i=0; i < numberOfExpectedPlayers; i++) {
            clients.get(i).send(ServerMessageGenerator.generateStartGameMessage());
            clients.get(i).send(ServerMessageGenerator.generateUpdateHandMessage(game.players[i].getHand()));
            clients.get(i).send(ServerMessageGenerator.generateDrawableCardsMessage(game.resourceCardDeck.getDrawableCardsId(),game.goldCardDeck.getDrawableCardsId()));
            this.sendUpDatedScores();
        }

        //while ()
    }

    /**
     * sends the updated scores to every player
     */
    public void sendUpDatedScores () {
        ArrayList <Integer> scores = new ArrayList<>();
        for (int i=0; i < numberOfExpectedPlayers; i++) {
            scores.add(game.players[i].getScore()); }

        ArrayList <String> names = new ArrayList<>();
        for (int i=0; i < numberOfExpectedPlayers; i++) {
            names.add(game.players[i].getUsername()); }

        for (int i=0; i < numberOfExpectedPlayers; i++) {
            clients.get(i).send(ServerMessageGenerator.generateUpdatedScoreMessage(names, scores));}
    }

    @Override
    public void notifyIncomingMessage(ClientHandler clientHandler) {
        JSONObject jsonMessage = clientHandler.getReceivedRequest();
        //TODO chiamare il pareser e agire di conseguenza
    }

    @Override
    public void notifyConnectionLoss(ClientHandler clientHandler) {

    }
}
