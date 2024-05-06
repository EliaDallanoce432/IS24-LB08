package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.ObjectivesModel;
import it.polimi.ingsw.client.view.StageManager;

public class ObjectivesObserver implements ModelObserver{

    public ObjectivesObserver() {
        ObjectivesModel.getIstance().addObserver(this);
    }



    @Override
    public void update() {

        StageManager.getViewController().updateObjectives();

    }
}
