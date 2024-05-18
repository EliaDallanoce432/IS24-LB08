package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.DeckModel;
import it.polimi.ingsw.client.view.StageManager;

/**
 * This class implements the ModelObserver interface
 * and is responsible for observing changes in the DeckModel. When a change
 * occurs, it updates the deck view through the StageManager.
 * This class is instantiated and registered as an observer to the DeckModel
 * singleton instance upon creation.
 */
public class DeckObserver implements ModelObserver {

    /**
     * Constructs a new DeckObserver and registers it as an observer
     * to the DeckModel instance.
     */
    public DeckObserver() {
        DeckModel.getInstance().addObserver(this);
    }

    /**
     * This method is called when the observed object is changed. It triggers
     * the update of the GUI through the StageManager.
     */
    @Override
    public void update() {
        StageManager.getCurrentViewController().updateDecks();
    }
}