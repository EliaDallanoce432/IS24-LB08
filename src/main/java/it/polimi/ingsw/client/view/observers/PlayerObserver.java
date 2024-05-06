package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.view.StageManager;

public class PlayerObserver implements ModelObserver {

    public PlayerObserver() {
        PlayerModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {
        StageManager.getViewController().updatePlayerInfo();
    }
}
