package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.HandModel;
import it.polimi.ingsw.client.view.SceneLoader;

public class HandObserver implements ModelObserver{

    public HandObserver() {
        HandModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {
        SceneLoader.getViewController().updateHand();
    }
}
