package it.polimi.ingsw.client.model;

public class SelectableCardsModel extends ObservableModel{

    private static SelectableCardsModel instance;

    private int starterCardId;
    private int[] selectableObjectiveCardsId;


    public static SelectableCardsModel getInstance(){

        if (instance ==null) instance = new SelectableCardsModel();
        return instance;

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
