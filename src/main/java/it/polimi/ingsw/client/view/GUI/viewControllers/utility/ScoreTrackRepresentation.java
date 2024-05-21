package it.polimi.ingsw.client.view.GUI.viewControllers.utility;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class ScoreTrackRepresentation {

    private Pane scoreTrackPane;
    private Circle startingPointReference;

    public ScoreTrackRepresentation(Pane scoreTrackPane, Circle startingPointReference){
        this.scoreTrackPane = scoreTrackPane;
        this.startingPointReference = startingPointReference;
    }

    public void loadTokens(){
        //TODO implementare
    }
}
