package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.VirtualCard;

import java.util.ArrayList;

public class GameFieldModel extends ObservableModel {

    private static GameFieldModel istance;

    private ArrayList<VirtualCard> placementHistory;

    public static GameFieldModel getIstance(){

        if (istance==null) istance = new GameFieldModel();
        return istance;

    }

    public ArrayList<VirtualCard> getPlacementHistory() {
        return placementHistory;
    }

    public void updatePlacementHistory(ArrayList<VirtualCard> placementHistory) {
        this.placementHistory = placementHistory;
        notifyObservers();
    }
}
