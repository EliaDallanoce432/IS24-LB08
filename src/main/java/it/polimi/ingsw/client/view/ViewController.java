package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.application.Platform;

import java.io.IOException;

public abstract class ViewController {



    public void showMessage(String message) {
        System.out.println(message);
    }

    public void updateDecks(){}

    public void updateGameBoard(){}

    public void updateScoreBoard(){}

    public void updateHand(){}

    public void updatePlayerInfo(){}


    public void loadStarterCard(int id){

        Platform.runLater(()->{
            try {
                SceneLoader.getCurrentStage().setScene(SceneLoader.loadChooseStarterCardScene(id));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void loadObjectiveCards(int id1, int id2){
        Platform.runLater(()->{
            try {
                SceneLoader.getCurrentStage().setScene(SceneLoader.loadChooseObjectiveCardScene(id1,id2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loadGameBoard(int starterCardId, int commonObjective1Id, int commonObjective2Id, int secretObjectiveId){
        Platform.runLater(()->{
            try {
                SceneLoader.getCurrentStage().setScene(SceneLoader.loadGameBoardScene(starterCardId,commonObjective1Id,commonObjective2Id,secretObjectiveId));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void closeGUI() {
        Platform.runLater(()->{SceneLoader.getCurrentStage().close();});
    }

}
