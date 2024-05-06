package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.GameFieldModel;

public class GameFieldObserver implements ModelObserver {

    public GameFieldObserver() {
        GameFieldModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {

    }
}
