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

    private static Stage currentStage;


    public static void setClientController(ClientController clientController) {
        SceneLoader.clientController = clientController;
    }

    public static void setCurrentStage(Stage currentStage) {
        SceneLoader.currentStage = currentStage;
    }

    public static Stage getCurrentStage() {
        return currentStage;
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
        clientController.setViewController(gameBoardViewController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 1600 , 900);
    }

    public static Scene loadWelcomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("WelcomeView.fxml"));
        Parent root = loader.load();
        WelcomeViewController welcomeViewController = loader.getController();
        welcomeViewController.setClientController(clientController);
        clientController.setViewController(welcomeViewController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadCreateGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("CreateGameView.fxml"));
        Parent root = loader.load();
        CreateGameViewController createGameViewController = loader.getController();
        createGameViewController.setClientController(clientController);
        clientController.setViewController(createGameViewController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadJoinGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("JoinGameView.fxml"));
        Parent root = loader.load();
        JoinGameViewController joinGameViewController = loader.getController();
        joinGameViewController.setClientController(clientController);
        clientController.setViewController(joinGameViewController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadWaitForPlayersScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("WaitForPlayersView.fxml"));
        Parent root = loader.load();
        WaitForPlayersViewController waitForPlayersViewController = loader.getController();
        waitForPlayersViewController.setClientController(clientController);
        clientController.setViewController(waitForPlayersViewController);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadChooseStarterCardScene(int id) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("ChooseCardsView.fxml"));
        Parent root = loader.load();
        ChooseCardsViewController chooseCardsViewController = loader.getController();
        chooseCardsViewController.setClientController(clientController);
        clientController.setViewController(chooseCardsViewController);
        chooseCardsViewController.showStarterCard(id);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public static Scene loadChooseObjectiveCardScene(int id1, int id2) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("ChooseCardsView.fxml"));
        Parent root = loader.load();
        ChooseCardsViewController chooseCardsViewController = loader.getController();
        chooseCardsViewController.setClientController(clientController);
        clientController.setViewController(chooseCardsViewController);
        chooseCardsViewController.showObjectiveCards(id1, id2);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }






}
