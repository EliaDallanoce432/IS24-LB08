package it.polimi.ingsw.client.view.utility;

import it.polimi.ingsw.client.model.ObjectivesModel;
import javafx.scene.layout.HBox;

/**
 * This class manages the representation of the Common and secret objectives
 */

public class ObjectivesRepresentation {

    HBox commonObjectivesPane;
    HBox secretObjectivePane;

    /**
     * Sets up the ObjectivesRepresentation
     * @param commonObjectivesPane the HBox in which the common objective cards will be shown
     * @param secretObjectivePane the HBox in which the secret objective card will be shown
     */

    public ObjectivesRepresentation(HBox commonObjectivesPane, HBox secretObjectivePane) {
        this.commonObjectivesPane = commonObjectivesPane;
        this.secretObjectivePane = secretObjectivePane;
    }

    /**
     * Loads the CommonObjectives from the ObjectivesModel
     */

    public void loadCommonObjectives (){

        int[] commonObjIds = ObjectivesModel.getIstance().getCommonObjectives();
        commonObjectivesPane.getChildren().clear();

        CardRepresentation cardRepresentation = new CardRepresentation(commonObjIds[0], true);
        commonObjectivesPane.getChildren().add(cardRepresentation.getCard());
        cardRepresentation = new CardRepresentation(commonObjIds[1], true);
        commonObjectivesPane.getChildren().add(cardRepresentation.getCard());

    }

    /**
     * Loads the SecretObjective from the ObjectivesModel
     */

    public void loadSecretObjective (){
        int ObjectiveCardId = ObjectivesModel.getIstance().getSecretObjectiveId();
        secretObjectivePane.getChildren().clear();

        CardRepresentation cardRepresentation = new CardRepresentation(ObjectiveCardId, true);
        secretObjectivePane.getChildren().add(cardRepresentation.getCard());

    }
}
