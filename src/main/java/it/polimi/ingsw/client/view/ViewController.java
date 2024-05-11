package it.polimi.ingsw.client.view;

import javafx.application.Platform;

public abstract class ViewController {



    public void showMessage(String message) {
        System.out.println(message);
    }

    public void updateDecks(){}

    public void updateGameBoard(){}

    public void updateScoreBoard(){}

    public void updateHand(){}

    public void updatePlayerInfo(){}

    public void updateSelectableCards(){}

    public void updateObjectives(){}

    public void updateSceneStatus(){}

    public void updateAvailableGames(){}


    public void closeGUI() {
        Platform.runLater(()->{
            StageManager.getCurrentStage().close();});
    }

}
