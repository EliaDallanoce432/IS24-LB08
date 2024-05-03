package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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

    public void closeGUI() {
        Platform.runLater(()->{SceneLoader.getCurrentStage().close();});
    }

}
