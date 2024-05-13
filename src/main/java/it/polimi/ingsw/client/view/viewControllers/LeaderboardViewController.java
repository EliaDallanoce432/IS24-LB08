package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.model.ScoreBoardModel;
import it.polimi.ingsw.client.view.StageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.json.simple.JSONObject;

public class LeaderboardViewController extends ViewController {

    @FXML
    VBox leaderboardVBox;

    @FXML
    Label leaderboardLabel;

    @FXML
    private void backToMainScreen(){
        StageManager.loadWelcomeScene();
    }

    @FXML
    private void initialize() {
        Platform.runLater(this::updateScoreBoard);
    }


    @Override
    public void updateScoreBoard(){

        Platform.runLater(()->{

            leaderboardVBox.getChildren().clear();

            int position = 1;

            for(JSONObject obj : ScoreBoardModel.getInstance().getLeaderboard()){
                Label scoreLabel = new Label();
                scoreLabel.setText( position + "° - " + obj.get("username") + ": " + obj.get("score").toString());
                scoreLabel.setStyle("-fx-font-weight: bold");
                scoreLabel.setFont(Font.font("Arial", 24));
                leaderboardVBox.getChildren().add(scoreLabel);
                position++;
            }

        });


    }
}
