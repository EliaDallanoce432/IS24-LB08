package it.polimi.ingsw.client.model;

import java.util.ArrayList;

/**
 * This class represents an ObservableModel that keeps track of a list of available games.
 */
public class AvailableGamesModel extends ObservableModel{

    private static AvailableGamesModel instance;

    private ArrayList<String> games;

    /**
     * returns the singleton instance of AvailableGamesModel
     * @return The singleton instance of AvailableGamesModel
     */
    public static AvailableGamesModel getInstance(){

        if (instance ==null) instance = new AvailableGamesModel();
        return instance;

    }

    private AvailableGamesModel(){
        games = new ArrayList<>();
    }

    public ArrayList<String> getGames() {
        return games;
    }

    public void setGames(ArrayList<String> games) {
        this.games = games;
        notifyObservers();
    }
}
