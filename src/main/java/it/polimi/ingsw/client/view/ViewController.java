package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.application.Platform;

import java.io.IOException;

public abstract class ViewController {



    protected ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void loadDecks(){
        System.out.println("can't load deck here");
    }

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


}
