package it.polimi.ingsw.client.model;

/**
 * This class represents an ObservableModel that keeps track of the objectives in a game, including both secret and common objectives.
 */
public class ObjectivesModel extends ObservableModel{

    private static ObjectivesModel instance;

    private int secretObjectiveId;
    private int[] commonObjectives;



    /**
     * Returns the singleton instance of ObjectivesModel.
     * @return The singleton instance of ObjectivesModel.
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

    /**
     * Sets the common objectives and notifies any registered observers that the data has changed.
     * @param commonObjectives The array containing the common objectives IDs.
     */
    public void setCommonObjectives(int[] commonObjectives) {
        this.commonObjectives = commonObjectives;
        notifyObservers();
    }

    public int getSecretObjectiveId() {
        return secretObjectiveId;
    }

    public void setSecretObjectiveId(int secretObjectiveId) {
        this.secretObjectiveId = secretObjectiveId;
    }

    /**
     * Resets the model.
     */
    public void clear(){
        if (commonObjectives != null) {
            commonObjectives[0] = 0;
            commonObjectives[1] = 0;
        }
        secretObjectiveId = 0;
    }
}
