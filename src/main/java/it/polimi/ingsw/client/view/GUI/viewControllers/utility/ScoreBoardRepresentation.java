package it.polimi.ingsw.client.view.GUI.viewControllers.utility;

import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.model.ScoreBoardModel;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class manages the representation of the Scoreboard
 */

public class ScoreBoardRepresentation {

    private VBox scoreBoardVBox;

    /**
     * Sets up the ScoreBoardRepresentation
     * @param scoreBoardVBox the Pane in which the scoreboard will be shown
     */

    public ScoreBoardRepresentation(VBox scoreBoardVBox) {
        this.scoreBoardVBox = scoreBoardVBox;

    }

    /**
     * Updates the scores from the PlayerModel
     */

    public void updateScores() {

        scoreBoardVBox.getChildren().clear();
        String clientUsername = PlayerModel.getInstance().getUsername();
        int clientScore = ScoreBoardModel.getInstance().getScore().getOrDefault(clientUsername, 0);
        Label clientLabel = new Label("You: " + clientScore + " Points");
        clientLabel.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD,17));
        clientLabel.setTextFill(PlayerModel.getInstance().getToken().toColor());
        scoreBoardVBox.getChildren().add(clientLabel);

        ScoreBoardModel.getInstance().getScore().forEach((username, score) -> {
            if (!username.equals(clientUsername)) {
                Label label = new Label(username + ": " + score + " Points");
                label.setFont(Font.font("Baskerville Old Face",17));
                label.setTextFill(ScoreBoardModel.getInstance().getToken(username).toColor());
                scoreBoardVBox.getChildren().add(label);
            }
        });
    }
}
