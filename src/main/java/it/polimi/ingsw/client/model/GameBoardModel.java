package it.polimi.ingsw.client.model;

public class GameBoardModel extends ObservableModel {

    private static GameBoardModel istance;

    public static GameBoardModel getIstance(){

        if (istance==null) istance = new GameBoardModel();
        return istance;

    }

    private int starterCardId;
    private boolean starterCardFacingUp;
    private int secretObjectiveId;

    public int getSecretObjectiveId() {
        return secretObjectiveId;
    }

    public void setSecretObjectiveId(int secretObjectiveId) {
        this.secretObjectiveId = secretObjectiveId;
    }

    public  int getStarterCardId() {
        return starterCardId;
    }

    public void setStarterCardId(int starterCardId ) {
        this.starterCardId = starterCardId;
    }

    public boolean isStarterCardFacingUp() {
        return starterCardFacingUp;
    }

    public void setStarterCardFacingUp(boolean starterCardFacingUp) {
        this.starterCardFacingUp = starterCardFacingUp;
    }


}
