package it.polimi.ingsw.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    public ImageView loadBackground(String path){
        Image backgroundImage = new Image(getClass().getResourceAsStream(path));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1600);
        backgroundImageView.setFitHeight(900);
        return backgroundImageView;
    }

    public Scene loadGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        Parent root = loader.load();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 1600 , 900);
    }

    public Scene loadWelcomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Parent root = loader.load();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }

    public Scene loadCreateGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateGameView.fxml"));
        Parent root = loader.load();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(loadBackground("/view/wood_background2.jpg"), root);
        return new Scene(stackPane, 600 , 400);
    }


}
