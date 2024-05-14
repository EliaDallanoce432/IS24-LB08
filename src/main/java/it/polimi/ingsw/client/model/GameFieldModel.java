package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.utility.CardRepresentation;

import java.util.ArrayList;

public class GameFieldModel extends ObservableModel {

    private static GameFieldModel istance;

    private ArrayList<CardRepresentation> placementHistory;

    public static GameFieldModel getIstance(){

        if (istance==null) istance = new GameFieldModel();
        return istance;

    }

    private GameFieldModel(){
        placementHistory = new ArrayList<>();
    }

    public ArrayList<CardRepresentation> getPlacementHistory() {
        return placementHistory;
    }

    public void updatePlacementHistory(ArrayList<CardRepresentation> placementHistory) {
        this.placementHistory = placementHistory;
        notifyObservers();
    }


    public void rollback(){
        notifyObservers();
    }
}
