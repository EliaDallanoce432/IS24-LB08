package it.polimi.ingsw.client.model;

public class SelectableCardsModel extends ObservableModel{

    private static SelectableCardsModel istance;

    private int starterCardId;
    private int[] selectableObjectiveCardsId;


    public static SelectableCardsModel getIstance(){

        if (istance==null) istance = new SelectableCardsModel();
        return istance;

    }

    public int[] getSelectableObjectiveCardsId() {
        return selectableObjectiveCardsId;
    }

    public void setStarterCardId(int starterCardId) {
        this.starterCardId = starterCardId;
        notifyObservers();
    }

    public void setSelectableObjectiveCardsId(int[] objectiveCardsId) {
        this.selectableObjectiveCardsId = objectiveCardsId;
        notifyObservers();
    }


    public int getStarterCardId() {
        return starterCardId;
    }
}
