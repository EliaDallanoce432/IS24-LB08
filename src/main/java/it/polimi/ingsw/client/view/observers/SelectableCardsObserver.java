package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.StageManager;
import javafx.application.Platform;

public class SelectableCardsObserver implements ModelObserver{

    public SelectableCardsObserver() {
        SelectableCardsModel.getInstance().addObserver(this);
    }

    @Override
    public void update() {
        Platform.runLater(()->StageManager.getViewController().updateSelectableCards());
    }
}
