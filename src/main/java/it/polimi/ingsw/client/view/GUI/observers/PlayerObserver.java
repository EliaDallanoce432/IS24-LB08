package it.polimi.ingsw.client.view.GUI.observers;

import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.view.StageManager;

/**
 * This class implements the ModelObserver interface
 * and is responsible for observing changes in the PlayerModel. When a change
 * occurs, it updates the GUI through the StageManager.
 * This class is instantiated and registered as an observer to the PlayerModel
 * singleton instance upon creation.
 */
public class PlayerObserver implements ModelObserver {

    /**
     * Constructs a new PlayerObserver and registers it as an observer
     * to the PlayerModel instance.
     */
    public PlayerObserver() {
        PlayerModel.getInstance().addObserver(this);
    }

    /**
     * This method is called when the observed object is changed. It triggers
     * the update of the GUI through the StageManager.
     */
    @Override
    public void update() {
        StageManager.getCurrentViewController().updatePlayerInfo();
    }
}
