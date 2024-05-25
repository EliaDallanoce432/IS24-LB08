package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.StageManager;

/**
 * This class implements the ModelObserver interface
 * and is responsible for observing changes in the SelectableCardsModel. When a change
 * occurs, it updates the GUI through the StageManager.
 * This class is instantiated and registered as an observer to the SelectableCardsModel
 * singleton instance upon creation.
 */
public class SelectableCardsObserver implements ModelObserver{

    /**
     * Constructs a new SelectableCardsObserver and registers it as an observer
     * to the SelectableCardsModel instance.
     */
    public SelectableCardsObserver() {
        SelectableCardsModel.getInstance().addObserver(this);
    }

    /**
     * This method is called when the observed object is changed. It triggers
     * the update of the GUI through the StageManager.
     */
    @Override
    public void update() {
        StageManager.getCurrentViewController().updateSelectableCards();
    }
}
