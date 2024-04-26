package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    private static ClientController clientController;

    public SceneLoader(ClientController clientController) {
        SceneLoader.clientController = clientController;
    }


    public static void setClientController(ClientController clientController) {
        SceneLoader.clientController = clientController;
    }

    private static ImageView loadBackground(String path){
        Image backgroundImage = new Image(SceneLoader.class.getResourceAsStream(path));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1600);
        backgroundImageView.setFitHeight(900);
        return backgroundImageView;
    }

    public static Scene loadGameBoardScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("GameBoardView.fxml"));
        Parent root = loader.load();
        GameBoardViewController gameBoardViewController = loader.getController();
        gameBoardViewController.setClientController(clientController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 1600 , 900);
    }

    public static Scene loadWelcomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("WelcomeView.fxml"));
        Parent root = loader.load();
        WelcomeViewController welcomeViewController = loader.getController();
        welcomeViewController.setClientController(clientController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadCreateGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("CreateGameView.fxml"));
        Parent root = loader.load();
        CreateGameViewController createGameViewController = loader.getController();
        createGameViewController.setClientController(clientController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadJoinGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("JoinGameView.fxml"));
        Parent root = loader.load();
        JoinGameViewController joinGameViewController = loader.getController();
        joinGameViewController.setClientController(clientController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadWaitForPlayersScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("WaitForPlayersView.fxml"));
        Parent root = loader.load();
        WaitForPlayersViewController waitForPlayersViewController = loader.getController();
        waitForPlayersViewController.setClientController(clientController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }




}
