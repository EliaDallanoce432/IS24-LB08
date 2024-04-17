package it.polimi.ingsw.client.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        Parent root = loader.load();

        Image backgroundImage = new Image(getClass().getResourceAsStream("/view/wood_background2.jpg"));
        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.setFitWidth(1500);
        backgroundImageView.setFitHeight(1000);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, root);

        Scene scene = new Scene(stackPane, 1500 , 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Codex - Game Demo");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}