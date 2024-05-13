package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.model.ScoreBoardModel;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ScoreBoardController {

    Pane scoreBoardPane;

    public ScoreBoardController(Pane scoreBoardPane) {
        this.scoreBoardPane = scoreBoardPane;

    }

    public void updateScores() {
        VBox scoreLabels = new VBox();

        String clientUsername = PlayerModel.getInstance().getUsername();
        int clientScore = ScoreBoardModel.getInstance().getScore().getOrDefault(clientUsername, 0);
        Label clientLabel = new Label("You: " + clientScore);
        scoreLabels.getChildren().add(clientLabel);

        ScoreBoardModel.getInstance().getScore().forEach((username, score) -> {
            if (!username.equals(clientUsername)) {
                Label label = new Label(username + ": " + score);
                scoreLabels.getChildren().add(label);
            }
        });

        scoreBoardPane.getChildren().add(scoreLabels);
    }
}
