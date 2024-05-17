package it.polimi.ingsw.client.model;

/**
 * This class represents an ObservableModel that keeps track of the objectives in a game, including both secret and common objectives
 */
public class ObjectivesModel extends ObservableModel{

    private static ObjectivesModel instance;

    private int secretObjectiveId;
    private int[] commonObjectives;

    public int getSecretObjectiveId() {
        return secretObjectiveId;
    }

    public void setSecretObjectiveId(int secretObjectiveId) {
        this.secretObjectiveId = secretObjectiveId;
    }

    /**
     * returns the singleton instance of ObjectivesModel
     * @return The singleton instance of ObjectivesModel
     */
    public static ObjectivesModel getInstance(){

        if (instance ==null) instance = new ObjectivesModel();
        return instance;

    }

    private ObjectivesModel(){
        commonObjectives = new int[2];
    }


    public int[] getCommonObjectives() {
        return commonObjectives;
    }

    public void setCommonObjectives(int[] commonObjectives) {
        this.commonObjectives = commonObjectives;
        notifyObservers();
    }
}
