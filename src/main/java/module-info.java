module LB08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires json.simple;

    requires org.controlsfx.controls;

    exports it.polimi.ingsw.client.controller;
    exports it.polimi.ingsw.client.view;
    exports it.polimi.ingsw;
    opens it.polimi.ingsw.client.view;
    opens it.polimi.ingsw.client.controller to javafx.fxml;
    exports it.polimi.ingsw.client.view.viewControllers;
    opens it.polimi.ingsw.client.view.viewControllers;
    exports it.polimi.ingsw.client.view.utility;
    opens it.polimi.ingsw.client.view.utility;
}