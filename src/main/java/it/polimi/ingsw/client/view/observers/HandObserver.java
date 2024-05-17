package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.HandModel;
import it.polimi.ingsw.client.view.StageManager;

/**
 * This class implements the ModelObserver interface
 * and is responsible for observing changes in the HandModel. When a change
 * occurs, it updates the GUI through the StageManager.
 * This class is instantiated and registered as an observer to the HandModel
 * singleton instance upon creation.
 */
public class HandObserver implements ModelObserver{
    /**
     * Constructs a new HandObserver and registers it as an observer
     * to the HandModel instance.
     */
    public HandObserver() {
        HandModel.getInstance().addObserver(this);
    }

    /**
     * This method is called when the observed object is changed. It triggers
     * the update of the GUI through the StageManager.
     */
    @Override
    public void update() {
        StageManager.getViewController().updateHand();
    }
}
