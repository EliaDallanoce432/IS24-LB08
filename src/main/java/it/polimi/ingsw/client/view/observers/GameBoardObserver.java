package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.GameBoardModel;

public class GameBoardObserver implements ModelObserver {

    public GameBoardObserver() {
        GameBoardModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {

    }
}
