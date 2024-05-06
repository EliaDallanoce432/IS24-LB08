package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.view.StageManager;

public class SelectableCardsObserver implements ModelObserver{

    @Override
    public void update() {
        StageManager.getViewController().updateSelectableCards();
    }
}
