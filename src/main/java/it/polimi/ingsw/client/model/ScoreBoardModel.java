package it.polimi.ingsw.client.model;

public class ScoreBoardModel extends ObservableModel{

    private static ScoreBoardModel istance;

    public static ScoreBoardModel getIstance(){

        if (istance==null) istance = new ScoreBoardModel();
        return istance;

    }

}
