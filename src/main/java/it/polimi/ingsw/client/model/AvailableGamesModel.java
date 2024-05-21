package it.polimi.ingsw.client.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents an ObservableModel that keeps track of a list of available games.
 */
public class AvailableGamesModel extends ObservableModel{

    private static AvailableGamesModel instance;

    private HashMap<String,String> games;

    /**
     * returns the singleton instance of AvailableGamesModel
     * @return The singleton instance of AvailableGamesModel
     */
    public static AvailableGamesModel getInstance(){

        if (instance ==null) instance = new AvailableGamesModel();
        return instance;

    }

    private AvailableGamesModel(){
        games = new HashMap<>();
    }

    public ArrayList<String> getGames() {
        ArrayList<String> displayedGames = new ArrayList<>();
        for (String gameName : games.keySet()) {
            displayedGames.add(gameName+ " - " + games.get(gameName));
        }
        return displayedGames;
    }

    public void setGames(HashMap<String,String> games) {
        this.games = games;
        notifyObservers();
    }
}
