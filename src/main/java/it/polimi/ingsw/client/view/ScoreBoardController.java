package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.model.ScoreBoardModel;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreBoardController {

    Pane scoreBoardPane;

    public ScoreBoardController(Pane scoreBoardPane) {
        this.scoreBoardPane = scoreBoardPane;

    }

    public void updateScores() {
        scoreBoardPane.getChildren().clear();
        VBox scoreLabels = new VBox();

        String clientUsername = PlayerModel.getInstance().getUsername();
        int clientScore = ScoreBoardModel.getInstance().getScore().getOrDefault(clientUsername, 0);
        Label clientLabel = new Label("You: " + clientScore);
        clientLabel.setFont(Font.font("Arial", FontWeight.BOLD,18));
        clientLabel.setTextFill(Color.WHITE);
        scoreLabels.getChildren().add(clientLabel);

        ScoreBoardModel.getInstance().getScore().forEach((username, score) -> {
            if (!username.equals(clientUsername)) {
                Label label = new Label(username + ": " + score);
                label.setFont(Font.font("Arial",20));
                label.setTextFill(Color.WHITE);
                scoreLabels.getChildren().add(label);
            }
        });

        scoreBoardPane.getChildren().add(scoreLabels);
    }
}
