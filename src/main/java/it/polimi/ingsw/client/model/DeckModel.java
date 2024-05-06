package it.polimi.ingsw.client.model;

public class DeckModel extends ObservableModel{

    private int resourceDeckTopCardId;
    private int resourceDeckLeftCardId;
    private int resourceDeckRightCardId;
    private int goldDeckTopCardId;
    private int goldDeckLeftCardId;
    private int goldDeckRightCardId;

    private static DeckModel istance;

    public static DeckModel getIstance(){

        if (istance==null) istance = new DeckModel();
        return istance;

    }

}
