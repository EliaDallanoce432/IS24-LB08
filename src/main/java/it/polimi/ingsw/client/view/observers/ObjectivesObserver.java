package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.ObjectivesModel;
import it.polimi.ingsw.client.view.StageManager;

/**
 * This class implements the ModelObserver interface
 * and is responsible for observing changes in the ObjectivesModel. When a change
 * occurs, it updates the GUI through the StageManager.
 * This class is instantiated and registered as an observer to the ObjectivesModel
 * singleton instance upon creation.
 */
public class ObjectivesObserver implements ModelObserver{

    /**
     * Constructs a new ObjectivesObserver and registers it as an observer
     * to the ObjectivesModel instance.
     */
    public ObjectivesObserver() {
        ObjectivesModel.getInstance().addObserver(this);
    }


    /**
     * This method is called when the observed object is changed. It triggers
     * the update of the GUI through the StageManager.
     */
    @Override
    public void update() {
        StageManager.getCurrentViewController().updateObjectives();
    }
}
