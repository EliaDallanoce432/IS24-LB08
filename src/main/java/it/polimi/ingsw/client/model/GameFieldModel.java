package it.polimi.ingsw.client.model;

public class GameFieldModel extends ObservableModel {

    private static GameFieldModel istance;

    public static GameFieldModel getIstance(){

        if (istance==null) istance = new GameFieldModel();
        return istance;

    }

    private int starterCardId;
    private boolean starterCardFacingUp;

    public  int getStarterCardId() {
        return starterCardId;
    }

    public boolean isStarterCardFacingUp() {
        return starterCardFacingUp;
    }

    public void setStarterCard(int starterCardId, boolean starterCardFacingUp ) {
        this.starterCardId = starterCardId;
        this.starterCardFacingUp = starterCardFacingUp;
        notifyObservers();
    }
}
