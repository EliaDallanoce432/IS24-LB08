package it.polimi.ingsw.client.model;

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
