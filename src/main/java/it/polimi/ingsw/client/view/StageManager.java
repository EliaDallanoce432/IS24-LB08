package it.polimi.ingsw.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {

    private static Stage currentStage;

    private static ViewController currentView;

    public static void setCurrentStage(Stage currentStage) {
        StageManager.currentStage = currentStage;
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }


    public static ViewController getViewController() {
        return currentView;
    }

    private static ImageView loadBackground(String path){
        Image backgroundImage = new Image(StageManager.class.getResourceAsStream(path));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1600);
        backgroundImageView.setFitHeight(900);
        return backgroundImageView;
    }

    public static void loadGameBoardScene() {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource("GameBoardView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameFieldViewController gameFieldViewController = loader.getController();
        currentView = gameFieldViewController;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        showScene( new Scene(stackPane, 1600 , 900));
    }

    public static void loadWelcomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource("WelcomeView.fxml"));
        Parent root = loader.load();
        WelcomeViewController welcomeViewController = loader.getController();
        currentView = welcomeViewController;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        showScene( new Scene(stackPane, 600 , 400));
    }

    public static void loadCreateGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource("CreateGameView.fxml"));
        Parent root = loader.load();
        CreateGameViewController createGameViewController = loader.getController();
        currentView = createGameViewController;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        showScene( new Scene(stackPane, 600 , 400));
    }

    public static void loadJoinGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource("JoinGameView.fxml"));
        Parent root = loader.load();
        JoinGameViewController joinGameViewController = loader.getController();
        currentView = joinGameViewController;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        showScene( new Scene(stackPane, 600 , 400));
    }

    public static void loadWaitForPlayersScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource("WaitForPlayersView.fxml"));
        Parent root = loader.load();
        WaitForPlayersViewController waitForPlayersViewController = loader.getController();
        currentView = waitForPlayersViewController;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        showScene( new Scene(stackPane, 600 , 400));
    }

    public static void loadChooseCardsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource("ChooseCardsView.fxml"));
        Parent root = loader.load();
        ChooseCardsViewController chooseCardsViewController = loader.getController();
        currentView = chooseCardsViewController;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        showScene( new Scene(stackPane, 600 , 400));
    }

    public static void loadLostConnectionScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource("LostConnectionView.fxml"));
        Parent root = loader.load();
        LostConnectionViewController lostConnectionViewController = loader.getController();
        currentView = lostConnectionViewController;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        showScene( new Scene(stackPane, 600 , 400));
    }

    private static void showScene(Scene scene) {
        currentStage.setScene(scene);
        currentStage.show();
    }


}
