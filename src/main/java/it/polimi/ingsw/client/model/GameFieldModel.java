package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.viewControllers.utility.CardRepresentation;
import java.util.ArrayList;

/**
 * This class represents an ObservableModel that keeps track of the game field state, specifically the placement history of cards
 */
public class GameFieldModel extends ObservableModel {

    private static GameFieldModel instance;
    private ArrayList<CardRepresentation> placementHistory;

    /**
     * returns the singleton instance of GameFieldModel
     * @return The singleton instance of GameFieldModel
     */
    public static GameFieldModel getInstance(){
        if (instance ==null) instance = new GameFieldModel();
        return instance;

    }

    private GameFieldModel(){
        placementHistory = new ArrayList<>();
    }

    public ArrayList<CardRepresentation> getPlacementHistory() {
        return placementHistory;
    }

    /**
     * updates the placement history with the provided new list
     * @param placementHistory The new list of CardRepresentation objects
     */
    public void updatePlacementHistory(ArrayList<CardRepresentation> placementHistory) {
        this.placementHistory = placementHistory;
        notifyObservers();
    }

    /**
     * triggers notification to registered observers without modifying the game field state
     */
    public void rollback(){
        notifyObservers();
    }
}
