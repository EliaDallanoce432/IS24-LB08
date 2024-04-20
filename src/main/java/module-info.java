module LB08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires json.simple;

    requires org.controlsfx.controls;

    opens it.polimi.ingsw.client.view to javafx.fxml;
    exports it.polimi.ingsw.client.view;
}