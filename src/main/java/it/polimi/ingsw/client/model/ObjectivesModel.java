package it.polimi.ingsw.client.model;

public class ObjectivesModel extends ObservableModel{

    private static ObjectivesModel istance;

    private int secretObjectiveId;
    private int[] commonObjectives;

    public int getSecretObjectiveId() {
        return secretObjectiveId;
    }

    public void setSecretObjectiveId(int secretObjectiveId) {
        this.secretObjectiveId = secretObjectiveId;
    }


    public static ObjectivesModel getIstance(){

        if (istance==null) istance = new ObjectivesModel();
        return istance;

    }




    public int[] getCommonObjectives() {
        return commonObjectives;
    }

    public void setCommonObjectives(int[] commonObjectives) {
        this.commonObjectives = commonObjectives;
        notifyObservers();
    }
}
