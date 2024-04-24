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
    public void place(ClientHandler player, PlaceableCard card, boolean facingUp, int x, int y) throws CannotPlaceCardException {
            game.players[clients.indexOf(player)].place(card, facingUp, x, y);
    }
    public void place(ClientHandler player, StarterCard card, boolean facingUp) { game.players[clients.indexOf(player)].place(card, facingUp);}
    public void directDrawResourceCard(ClientHandler player) throws EmptyDeckException, FullHandException {
        ResourceCard cardTemp = (ResourceCard)game.resourceCardDeck.directDraw();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public void directDrawGoldCard(ClientHandler player) throws EmptyDeckException, FullHandException {
        GoldCard cardTemp = (GoldCard)game.goldCardDeck.directDraw();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public void drawLeftRevealedResourceCard(ClientHandler player) throws FullHandException {
        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getLeftRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public void drawRightRevealedResourceCard(ClientHandler player) throws FullHandException {
        ResourceCard cardTemp = (ResourceCard) game.resourceCardDeck.getRightRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public void drawLeftRevealedGoldCard(ClientHandler player) throws FullHandException {
        GoldCard cardTemp = (GoldCard) game.goldCardDeck.getLeftRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public void drawRightRevealedGoldCard(ClientHandler player) throws FullHandException {
        GoldCard cardTemp = (GoldCard) game.goldCardDeck.getRightRevealedCard();
        game.players[clients.indexOf(player)].addToHand(cardTemp);
    }
    public void chooseStarterCardOrientations(ClientHandler player, int starterCardId, boolean facingUp) {
        for (StarterCard card : drawnStarterCards) {
             if (card.getId() == starterCardId) {
                 game.players[clients.indexOf(player)].place(drawnStarterCards.remove(starterCardId),facingUp);
             }
        }
    }
    public void chooseSecretObjectiveCard(ClientHandler player, int objectiveCardId) {
        for (ObjectiveCard card : drawnObjectiveCards) {
            if (card.getId() == objectiveCardId) {
                game.players[clients.indexOf(player)].setSecretObjective(drawnObjectiveCards.remove(objectiveCardId));
            }
        }
    }
    public void run() {
        waitForEveryoneToJoinAndBeReady();
        gamePreparation();
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
                clients.get(i).send(ServerMessageGenerator.generateDrawnObjectiveCardsMessage(cardtemp1, cardtemp2));
            } catch (EmptyDeckException ignored) {}
        }
        while(drawnObjectiveCards.size()>numberOfExpectedPlayers)
        {
            //wait for every player to choose starter card orientation
        }
    }
    public  void ready(ClientHandler clientHandler) { clientHandler.setReady(true);}

    @Override
    public void notifyIncomingMessage(ClientHandler clientHandler) {
        JSONObject jsonMessage = clientHandler.getReceivedRequest();
        //TODO chiamare il pareser e agire di conseguenza
    }

    @Override
    public void notifyConnectionLoss(ClientHandler clientHandler) {

    }
}
