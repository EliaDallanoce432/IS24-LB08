package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.DeckModel;
import it.polimi.ingsw.client.view.StageManager;

public class DeckObserver implements ModelObserver {

    public DeckObserver() {
        DeckModel.getIstance().addObserver(this);
    }


    @Override
    public void update() {
        StageManager.getViewController().updateDecks();
    }
}
