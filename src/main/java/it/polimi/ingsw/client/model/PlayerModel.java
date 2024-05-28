package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.Token;

/**
 * This class represents an ObservableModel that keeps track of player information in a game.
 */
public class PlayerModel extends ObservableModel {

    private static PlayerModel instance;
    private String username;
    private String turnPlayer;
    private Token token;
    private boolean lastTurn;

    public static PlayerModel getInstance(){
        if (instance ==null) instance = new PlayerModel();
        return instance;
    }

    private PlayerModel(){
        username = "Guest";
        turnPlayer = " ";
        token = Token.red;
    }

    public String getTurnPlayer(){return turnPlayer;}

    public void setTurnPlayer(String turnPlayer){this.turnPlayer = turnPlayer;}

    public String getUsername() {
        return username;
    }

    /**
     * Sets the player's username and notifies any registered observers that the data has changed.
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
        notifyObservers();
    }

    public Token getToken() {
        return token;
    }

    /**
     * Sets the player's token and notifies any registered observers that the data has changed.
     * @param token The assigned token.
     */
    public void setToken(Token token) {
        this.token = token;
        notifyObservers();
    }

    public boolean isLastRound() {
        return lastTurn;
    }

    public void setLastTurn(boolean lastTurn) {
        this.lastTurn = lastTurn;
    }

    /**
     * Resets the LastTurn attribute to false.
     */
    public void clear(){
        lastTurn = false;
    }
}
