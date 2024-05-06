package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.ScoreBoardModel;
import it.polimi.ingsw.client.view.StageManager;

public class ScoreBoardObserver implements ModelObserver {

    public ScoreBoardObserver() {
        ScoreBoardModel.getIstance().addObserver(this);
    }


    @Override
    public void update() {
        StageManager.getViewController().updateScoreBoard();
    }
}
