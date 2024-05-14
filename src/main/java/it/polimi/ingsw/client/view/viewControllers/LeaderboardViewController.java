package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.model.PlayerModel;
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

    private String[] positions;

    @FXML
    private void backToMainScreen(){
        StageManager.loadWelcomeScene();
    }

    @FXML
    private void initialize() {
        positions = new String[]{"1st", "2nd", "3rd", "4th"};
        Platform.runLater(this::updateScoreBoard);
    }


    @Override
    public void updateScoreBoard(){

        Platform.runLater(()->{

            leaderboardVBox.getChildren().clear();

            int pos_index = 0;

            for(JSONObject obj : ScoreBoardModel.getInstance().getLeaderboard()){
                Label scoreLabel = new Label();
                scoreLabel.setText( positions[pos_index] + " - " + obj.get("username").toString() + ": " + obj.get("score").toString() + " Points (" + obj.get("solvedObjectives").toString() + " solved Objectives)" );
                scoreLabel.setStyle("-fx-font-weight: bold");
                scoreLabel.setFont(Font.font("Arial", 24));
                leaderboardVBox.getChildren().add(scoreLabel);


                if(obj.get("username").toString() == PlayerModel.getInstance().getUsername()){

                    if(pos_index == 0){
                        leaderboardLabel.setText("You Won!");
                    }
                    else {
                        leaderboardLabel.setText("You came in " + positions[pos_index] + " place!");
                    }
                }

                pos_index++;
            }

        });


    }
}