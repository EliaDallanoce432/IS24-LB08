package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.HandModel;
import it.polimi.ingsw.client.view.StageManager;

public class HandObserver implements ModelObserver{

    public HandObserver() {
        HandModel.getInstance().addObserver(this);
    }

    @Override
    public void update() {
        StageManager.getViewController().updateHand();
    }
}
