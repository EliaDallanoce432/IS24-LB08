package it.polimi.ingsw.client.model;

/**
 * This class represents an ObservableModel that keeps track of the selectable starter card and objective cards in the game setup phase.
 */
public class SelectableCardsModel extends ObservableModel{

    private static SelectableCardsModel instance;

    private int starterCardId;
    private int[] selectableObjectiveCardsId;

    /**
     * returns the singleton instance of SelectableCardsModel
     * @return  The singleton instance of SelectableCardsModel
     */
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
