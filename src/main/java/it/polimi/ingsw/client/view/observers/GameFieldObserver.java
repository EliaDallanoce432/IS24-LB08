package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.view.StageManager;

public class GameFieldObserver implements ModelObserver {

    public GameFieldObserver() {
        GameFieldModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {
        StageManager.getViewController().updateGameBoard();
    }
}
