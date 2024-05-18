package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.viewControllers.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

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
        backgroundImageView.setPreserveRatio(false);
        return backgroundImageView;
    }

    private static StackPane createStackPaneWithBackground(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource(fxmlPath));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentView = loader.getController();
        StackPane stackPane = new StackPane();
        ImageView backgroundImageView = loadBackground("/Images/Backgrounds/wood_background.jpg");
        stackPane.getChildren().add(backgroundImageView);
        stackPane.getChildren().add(root);
        backgroundImageView.fitWidthProperty().bind(stackPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stackPane.heightProperty());


        return stackPane;
    }

    public static void loadGameBoardScene() {
        StackPane stackPane = createStackPaneWithBackground("GameBoardView.fxml");
        stackPane.prefWidthProperty().bind(currentStage.widthProperty());
        stackPane.prefHeightProperty().bind(currentStage.heightProperty());
        showScene(new Scene(stackPane));
    }

    public static void loadWelcomeScene() {
        StackPane stackPane = createStackPaneWithBackground("WelcomeView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    public static void loadCreateGameScene() {
        StackPane stackPane = createStackPaneWithBackground("CreateGameView.fxml");
        showScene(new Scene(stackPane));
    }

    public static void loadJoinGameScene() {
        StackPane stackPane = createStackPaneWithBackground("JoinGameView.fxml");
        showScene(new Scene(stackPane));
    }

    public static void loadWaitForPlayersScene() {
        StackPane stackPane = createStackPaneWithBackground("WaitForPlayersView.fxml");
        showScene(new Scene(stackPane));
    }

    public static void loadChooseCardsScene() {
        StackPane stackPane = createStackPaneWithBackground("ChooseCardsView.fxml");
        showScene(new Scene(stackPane));
    }

    public static void loadLostConnectionScene() {
        StackPane stackPane = createStackPaneWithBackground("LostConnectionView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    public static void loadKickedFromGameScene(){
        StackPane stackPane = createStackPaneWithBackground("KickedFromGameView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    public static void loadLeaderboardScene() {
        StackPane stackPane = createStackPaneWithBackground("LeaderboardView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    private static void showScene(Scene scene) {
        currentStage.setOnCloseRequest(event -> System.exit(0));
        currentStage.setScene(scene);
        currentStage.sizeToScene();
        currentStage.show();
    }
}



