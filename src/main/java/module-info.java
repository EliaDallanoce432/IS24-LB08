module LB08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires json.simple;
    requires org.controlsfx.controls;

    exports it.polimi.ingsw;
    exports it.polimi.ingsw.server.lobby;
    exports it.polimi.ingsw.server.model;
    exports it.polimi.ingsw.server.controller;
    exports it.polimi.ingsw.server.view;
    exports it.polimi.ingsw.client.model;
    exports it.polimi.ingsw.client.controller;
    exports it.polimi.ingsw.client.view;
    exports it.polimi.ingsw.network;
    exports it.polimi.ingsw.util.customexceptions;
    exports it.polimi.ingsw.util.supportclasses;
}