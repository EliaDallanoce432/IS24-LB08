package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.AvailableGamesModel;
import it.polimi.ingsw.client.view.StageManager;

public class AvailableGamesModelObserver implements ModelObserver {

    public AvailableGamesModelObserver() {
        AvailableGamesModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {
        StageManager.getViewController().updateAvailableGames();
    }
}
