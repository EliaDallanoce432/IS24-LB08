package it.polimi.ingsw.client.view.GUI.observers;

import it.polimi.ingsw.client.model.AvailableGamesModel;
import it.polimi.ingsw.client.view.StageManager;

/**
 * This class implements the ModelObserver interface
 * and is responsible for observing changes in the AvailableGamesModel. When a change
 * occurs, it updates the GUI through the StageManager.
 * This class is instantiated and registered as an observer to the AvailableGamesModel
 * singleton instance upon creation.
 */
public class AvailableGamesObserver implements ModelObserver {

    /**
     * Constructs a new AvailableGamesObserver and registers it as an observer
     * to the AvailableGamesModel instance.
     */
    public AvailableGamesObserver() {
        AvailableGamesModel.getInstance().addObserver(this);
    }

    /**
     * This method is called when the observed object is changed. It triggers
     * the update of the GUI through the StageManager.
     */
    @Override
    public void update() {
        StageManager.getCurrentViewController().updateAvailableGames();
    }
}
