package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.view.StageManager;
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

    public void updateSceneStatus(){

        Platform.runLater(()->{
            System.out.println("UPDATE STATUS: " + ClientStateModel.getIstance().getClientState());

            switch (ClientStateModel.getIstance().getClientState()){
                case KICKED_STATE -> StageManager.loadKickedFromGameScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                default -> {}
            }
        });

    }

    public void updateAvailableGames(){}

}
