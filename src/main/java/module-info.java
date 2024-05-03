module LB08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires json.simple;

    requires org.controlsfx.controls;

    exports it.polimi.ingsw;
    opens it.polimi.ingsw.client.controller to javafx.fxml;
}