package it.polimi.ingsw.client.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        SceneLoader sceneLoader = new SceneLoader();

        primaryStage.setScene(sceneLoader.loadWelcomeScene());
        primaryStage.setTitle("Codex - Game Demo");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }




}