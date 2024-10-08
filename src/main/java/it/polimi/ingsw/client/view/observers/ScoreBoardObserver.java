package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.ScoreBoardModel;
import it.polimi.ingsw.client.view.StageManager;

/**
 * This class implements the ModelObserver interface
 * and is responsible for observing changes in the ScoreBoardModel. When a change
 * occurs, it updates the GUI through the StageManager.
 * This class is instantiated and registered as an observer to the ScoreBoardModel
 * singleton instance upon creation.
 */
public class ScoreBoardObserver implements ModelObserver {

    /**
     * Constructs a new ScoreBoardObserver and registers it as an observer
     * to the ScoreBoardModel instance.
     */
    public ScoreBoardObserver() {
        ScoreBoardModel.getInstance().addObserver(this);
    }

    /**
     * This method is called when the observed object is changed. It triggers
     * the update of the GUI through the StageManager.
     */
    @Override
    public void update() {
        StageManager.getCurrentViewController().updateScoreBoard();
    }
}
